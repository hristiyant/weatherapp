package com.hristiyantodorov.weatherapp.service;

import com.hristiyantodorov.weatherapp.persistence.PersistenceDatabase;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastCurrentlyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDataDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastDailyDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDao;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastFullDbModel;
import com.hristiyantodorov.weatherapp.model.database.forecast.ForecastHourlyDbModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ForecastDbService {

    private ForecastFullDao forecastFullDao;

    @Inject
    public ForecastDbService(PersistenceDatabase persistenceDatabase) {
        this.forecastFullDao = persistenceDatabase.getForecastFullDao();
    }

    public Single<ForecastFullDbModel> getForecastFullRx() {
        return forecastFullDao.getForecastFullRx();
    }

    public ForecastFullDbModel getForecastFull() {
        return forecastFullDao.getForecastFull();
    }

    public Single<List<ForecastCurrentlyDbModel>> getForecastCurrentlyByFullId(long id) {
        return forecastFullDao.getForecastCurrentlyByFullId(id);
    }

    public Single<List<ForecastCurrentlyDbModel>> getForecastHourlyDataByHourlyId(long id) {
        return forecastFullDao.getForecastHourlyDataByHourlyId(id);
    }

    public ForecastHourlyDbModel getForecastHourly() {
        return forecastFullDao.getForecastHourly();
    }

    public Single<ForecastHourlyDbModel> getForecastHourlyById(long id) {
        return forecastFullDao.getForecastHourlyById(id);
    }

    public ForecastDailyDbModel getForecastDaily() {
        return forecastFullDao.getForecastDaily();
    }

    public Single<ForecastDailyDbModel> getForecastDailyById(long id) {
        return forecastFullDao.getForecastDailyById(id);
    }

    public Single<List<ForecastDailyDataDbModel>> getForecastDailyDataByDailyId(long id) {
        return forecastFullDao.getForecastDailyDataByDailyId(id);
    }

    public int countForecastDataElements() {
        return forecastFullDao.countForecastDataElements();
    }

    public void updateDb(ForecastFullDbModel model) {
        if (forecastFullDao.countForecastDataElements() > 0) {
            updateForecastFull(model);
        } else {
            insertForecastFull(model, model.getCurrentlyDbModel());
        }
    }

    private void updateForecastFull(ForecastFullDbModel fullDbModel) {
        long forecastFullId = forecastFullDao.getForecastFull().getId();
        if (fullDbModel != null) {
            forecastFullDao.updateForecastFull(forecastFullId,
                    fullDbModel.getLatitude(),
                    fullDbModel.getLongitude(),
                    fullDbModel.getTimezone(),
                    fullDbModel.getLastUpdatedTimestamp()
            );
            updateDbWithCurrently(forecastFullId, fullDbModel.getCurrentlyDbModel());
        }

        assert fullDbModel != null;
        if (fullDbModel.getHourlyDbModel() != null) {
            updateDbWithHourly(fullDbModel);
        }

        ForecastDailyDbModel dailyDbModel = fullDbModel.getDailyDbModel();
        if (dailyDbModel != null) {
            updateDbWithDaily(fullDbModel);
        }
    }

    private void updateDbWithCurrently(long forecastFullId, ForecastCurrentlyDbModel currentlyModel) {
        forecastFullDao.updateForecastCurrently(forecastFullId,
                currentlyModel.getTime(),
                currentlyModel.getSummary(),
                currentlyModel.getIcon(),
                currentlyModel.getTemperature(),
                currentlyModel.getApparentTemperature(),
                currentlyModel.getHumidity(),
                currentlyModel.getPressure(),
                currentlyModel.getWindSpeed());
    }

    private void updateDbWithHourly(ForecastFullDbModel fullModel) {
        ForecastHourlyDbModel model = fullModel.getHourlyDbModel();
        long forecastHourlyId = forecastFullDao.getForecastHourly().getHourlyId();
        forecastFullDao.updateForecastHourly(forecastHourlyId, model.getSummary(), model.getIcon());
        updateForecastHourlyData(fullModel.getHourlyDataDbModels(), forecastHourlyId);
    }

    private void updateForecastHourlyData(List<ForecastCurrentlyDbModel> hourlyData, long forecastHourlyId) {
        forecastFullDao.dropForecastHourlyData();

        for (ForecastCurrentlyDbModel hourlyDbModel : hourlyData) {
            hourlyDbModel.setForecastHourlyId(forecastHourlyId);
            forecastFullDao.insert(hourlyDbModel);
        }
    }

    private void updateDbWithDaily(ForecastFullDbModel fullModel) {
        ForecastDailyDbModel model = fullModel.getDailyDbModel();
        long forecastDailyId = forecastFullDao.getForecastDaily().getDailyId();
        forecastFullDao.updateForecastDaily(forecastDailyId, model.getSummary(), model.getIcon());
        updateForecastDailyData(fullModel.getDailyDataDbModels(), forecastDailyId);
    }

    private void updateForecastDailyData(List<ForecastDailyDataDbModel> dailyData, long forecastDailyId) {
        forecastFullDao.dropForecastDailyData();

        for (ForecastDailyDataDbModel dailyDbModel : dailyData) {
            dailyDbModel.setForecastDailyId(forecastDailyId);
            forecastFullDao.insert(dailyDbModel);
        }
    }

    private void insertForecastFull(ForecastFullDbModel fullModel, ForecastCurrentlyDbModel currentlyModel) {
        long id = forecastFullDao.insert(fullModel);

        insertForecastCurrently(id, currentlyModel);
        if (fullModel.getDailyDbModel() != null) {
            insertForecastDaily(id, fullModel.getDailyDbModel(), fullModel.getDailyDataDbModels());
        }
        if (fullModel.getHourlyDbModel() != null) {
            insertForecastHourly(id, fullModel.getHourlyDbModel(), fullModel.getHourlyDataDbModels());
        }
    }

    private void insertForecastCurrently(long fullId, ForecastCurrentlyDbModel currentlyModel) {
        currentlyModel.setForecastFullId(fullId);
        forecastFullDao.insert(currentlyModel);
    }

    private void insertForecastHourly(long fullId, ForecastHourlyDbModel hourlyDbModel, List<ForecastCurrentlyDbModel> hourlyDataDbModels) {
        hourlyDbModel.setForecastFullId(fullId);
        long hourlyId = forecastFullDao.insert(hourlyDbModel);
        insertForecastHourlyData(hourlyId, hourlyDataDbModels);
    }

    private void insertForecastHourlyData(long hourlyId, List<ForecastCurrentlyDbModel> hourlyDataDbModels) {
        for (ForecastCurrentlyDbModel hourlyDbModel : hourlyDataDbModels) {
            hourlyDbModel.setForecastHourlyId(hourlyId);
            forecastFullDao.insert(hourlyDbModel);
        }
    }

    private void insertForecastDaily(long fullId, ForecastDailyDbModel dailyModel, List<ForecastDailyDataDbModel> dailyDataDbModels) {
        dailyModel.setForecastFullId(fullId);
        long dailyId = forecastFullDao.insert(dailyModel);
        insertForecastDailyData(dailyId, dailyDataDbModels);
    }

    private void insertForecastDailyData(long dailyId, List<ForecastDailyDataDbModel> dailyDataDbModels) {
        for (ForecastDailyDataDbModel dailyDataDbModel : dailyDataDbModels) {
            dailyDataDbModel.setForecastDailyId(dailyId);
            forecastFullDao.insert(dailyDataDbModel);
        }
    }
}