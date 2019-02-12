package com.hristiyantodorov.weatherapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.hristiyantodorov.weatherapp.model.AppDatabase;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDao;
import com.hristiyantodorov.weatherapp.persistence.location.LocationDbModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class LocationDatabaseTests {
    private LocationDao locationDao;
    private AppDatabase testDatabase;

    @Before
    public void createDB() {
        Context context = App.getInstance().getApplicationContext();
        testDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        locationDao = testDatabase.locationDao();
    }

    @After
    public void closeDB() {
        testDatabase.close();
    }

    @Test
    public void When_InsertLocations_Expect_DatabaseNotEmpty() {
        locationDao.insertLocations(
                new LocationDbModel("test1", 1.2, 1.3, "url1"),
                new LocationDbModel("test2", 2.2, 2.3, "url2"),
                new LocationDbModel("test3", 3.2, 3.3, "url3")
        );
        List<LocationDbModel> locations = locationDao.getAllLocations();
        Assert.assertEquals(3, locations.size());
    }

    @Test
    public void When_DeleteLocations_Expect_DatabaseEmpty() {
        LocationDbModel testLocation =
                new LocationDbModel("test1", 1.2, 1.3, "url1");
        locationDao.insertLocations(testLocation);
        List<LocationDbModel> locationsBeforeDelete = locationDao.getAllLocations();
        locationDao.deleteLocations(locationsBeforeDelete.get(0));
        List<LocationDbModel> locationsAfterDelete = locationDao.getAllLocations();
        Assert.assertTrue(!locationsBeforeDelete.isEmpty() && locationsAfterDelete.isEmpty());
    }

    @Test
    public void When_GetLocationByName_Expect_NotNull() {
        LocationDbModel testLocation =
                new LocationDbModel("test1", 1.2, 1.3, "url1");
        locationDao.insertLocations(testLocation);
        LocationDbModel testLocationToCompare = locationDao.getLocationByName("test1");
        Assert.assertEquals(testLocation.getName(), testLocationToCompare.getName());
    }
}
