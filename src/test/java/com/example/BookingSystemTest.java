package com.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingSystemTest {
    BookingSystem testSystem;
    LocalDateTime testStart = LocalDateTime.of(1999, 1, 2, 0, 0);
    LocalDateTime testEnd = LocalDateTime.of(1999, 1, 2, 23, 59);
    List<Room> testRoomList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        TimeProvider testTime = () -> LocalDateTime.of(1999, 1, 1, 0, 0);

        Room testRoom1 = new Room("1", "test1");
        Room testRoom2 = new Room("2", "test2");
        Room testRoom3 = new Room("3", "test3");

        testRoomList.add(testRoom1);
        testRoomList.add(testRoom2);
        testRoomList.add(testRoom3);

        RoomRepository testRoomRepository = new RoomRepository() {
            @Override
            public Optional<Room> findById(String id) {
                return Optional.ofNullable(testRoomList.stream().filter(room -> room.getId().equals(id)).findFirst().orElse(null));
            }

            @Override
            public List<Room> findAll() {
                return testRoomList;
            }

            @Override
            public void save(Room room) {

            }
        };

        NotificationService testNotification = new NotificationService() {
            @Override
            public void sendBookingConfirmation(Booking booking) throws NotificationException {

            }

            @Override
            public void sendCancellationConfirmation(Booking booking) throws NotificationException {

            }
        };
       testSystem = new BookingSystem(testTime, testRoomRepository, testNotification);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void bookRoomTest() {
        String testID = "1";

        Assertions.assertThat(testSystem.bookRoom(testID, testStart, testEnd));
    }

    @Test
    void getAvailableRoomsTest() {
        Assertions.assertThat(testSystem.getAvailableRooms(testStart, testEnd).equals(testRoomList));
    }

    @Test
    void cancelBookingTest() {
    }
}