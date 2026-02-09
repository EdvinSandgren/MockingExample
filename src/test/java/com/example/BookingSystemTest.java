package com.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingSystemTest {
    BookingSystem testSystem;


    @BeforeEach
    void setUp() {
        TimeProvider testTime = () -> LocalDateTime.of(1999, 1, 1, 0, 0);

        Room testRoom = new Room("1", "test");

        RoomRepository testRoomRepository = new RoomRepository() {
            @Override
            public Optional<Room> findById(String id) {
                return Optional.of(testRoom);
            }

            @Override
            public List<Room> findAll() {
                return List.of();
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
        LocalDateTime testStart = LocalDateTime.of(1999, 1, 2, 0, 0);
        LocalDateTime testEnd = LocalDateTime.of(1999, 1, 2, 23, 59);

        Assertions.assertThat(testSystem.bookRoom(testID, testStart, testEnd));
    }

    @Test
    void getAvailableRooms() {

    }

    @Test
    void cancelBooking() {
    }
}