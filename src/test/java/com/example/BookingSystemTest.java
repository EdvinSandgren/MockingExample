package com.example;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class BookingSystemTest {
    private BookingSystem testSystem;
    private final LocalDateTime testStart = LocalDateTime.of(1999, 1, 3, 0, 0);
    private final LocalDateTime testEnd = LocalDateTime.of(1999, 1, 4, 23, 59);
    private final List<Room> testRoomList = new ArrayList<>();
    private final List<Booking> bookingList = new ArrayList<>();
    private TimeProvider testTime;
    private RoomRepository testRoomRepository;
    private NotificationService testNotification;

    @BeforeEach
    void setUp() {
        testTime = () -> LocalDateTime.of(1999, 1, 2, 0, 0);

        Room testRoom1 = new Room("1", "test1");
        Room testRoom2 = new Room("2", "test2");
        Room testRoom3 = new Room("3", "test3");

        testRoomList.add(testRoom1);
        testRoomList.add(testRoom2);
        testRoomList.add(testRoom3);

        testRoomRepository = new RoomRepository() {
            @Override
            public Optional<Room> findById(String id) {
                return testRoomList.stream().filter(room -> room.getId().equals(id)).findFirst();
            }

            @Override
            public List<Room> findAll() {
                return testRoomList;
            }

            @Override
            public void save(Room room) {
                var replace = testRoomList.stream().filter(r ->  r.getId().equals(room.getId())).findFirst().orElse(null);
                testRoomList.set(testRoomList.indexOf(replace), room);
            }
        };

        testNotification = new NotificationService() {
            @Override
            public void sendBookingConfirmation(Booking booking) throws NotificationException {
                bookingList.add(booking);
            }

            @Override
            public void sendCancellationConfirmation(Booking booking) throws NotificationException {
                bookingList.remove(booking);
            }
        };
       testSystem = new BookingSystem(testTime, testRoomRepository, testNotification);
    }

    /// Tests the bookRoom method when booking an existing, available room
    @Test
    void bookRoomTest() {
        String testID = "1";
        Assertions.assertThat(testSystem.bookRoom(testID, testStart, testEnd)).isTrue();
    }

    /// Tests the bookRoom method when roomId is null
    @Test
    void bookRoomNullTest() {
        Assertions.assertThatThrownBy(() -> testSystem.bookRoom(null, testStart, testEnd)).hasMessage("Bokning kräver giltiga start- och sluttider samt rum-id");
    }

    /// Tests the bookRoom method when the start time is before the current test date
    @Test
    void bookRoomStartBeforeNowTest() {
        String testID = "1";
        Assertions.assertThatThrownBy(() -> testSystem.bookRoom(testID, LocalDateTime.of(1999, 1, 1, 0, 0), testEnd)).hasMessage("Kan inte boka tid i dåtid");
    }

    /// Tests the bookRoom method when the end time is before the start time
    @Test
    void bookRoomEndBeforeStartTest() {
        String testID = "1";
        Assertions.assertThatThrownBy(() -> testSystem.bookRoom(testID, testEnd, testStart)).hasMessage("Sluttid måste vara efter starttid");
    }

    /// Tests the bookRoom method when a room is already booked
    @Test
    void bookRoomAlreadyBookedTest() {
        String testID = "1";
        testSystem.bookRoom(testID, testStart, testEnd);
        Assertions.assertThat(testSystem.bookRoom(testID, testStart, testEnd)).isFalse();
    }

    /// Tests the getAvailableRooms method for a period of time
    @Test
    void getAvailableRoomsTest() {
        Assertions.assertThat(testSystem.getAvailableRooms(testStart, testEnd).equals(testRoomList)).isTrue();
    }

    /// Tests the getAvailableRooms method when the time is null
    @Test
    void getAvailableRoomsNoTimeTest() {
        Assertions.assertThatThrownBy(() -> testSystem.getAvailableRooms(null, null)).hasMessage("Måste ange både start- och sluttid");
    }

    /// Tests the getAvailableRooms method when the end time is before the start time
    @Test
    void getAvailableRoomsEndBeforeStartTest() {
        Assertions.assertThatThrownBy(() -> testSystem.getAvailableRooms(testEnd, testStart)).hasMessage("Sluttid måste vara efter starttid");
    }

    /// Tests the cancelBooking method when trying to cancel an existing booking
    @Test
    void cancelBookingTest() {
        String testID = "1";
        testSystem.bookRoom(testID, testStart, testEnd);
        Assertions.assertThat(testSystem.cancelBooking(bookingList.getFirst().getId())).isTrue();
    }

    /// Tests the cancelBooking method when trying to cancel with a null bookingId
    @Test
    void cancelBookingNullIdTest() {
        Assertions.assertThatThrownBy(() -> testSystem.cancelBooking(null)).hasMessage("Boknings-id kan inte vara null");
    }

    /// Tests the cancelBooking method when trying to cancel a booking that doesn't exist
    @Test
    void cancelBookingEmptyBookingTest() {
        Assertions.assertThat(testSystem.cancelBooking("")).isFalse();
    }

    /// Tests the cancelBooking method when trying to cancel an already started booking
    @Test
    void cancelBookingLateTest() {
        String testID = "1";
        testSystem.bookRoom(testID, testStart, testEnd);
        testTime = () -> testEnd;
        testSystem = new BookingSystem(testTime, testRoomRepository, testNotification);
        Assertions.assertThatThrownBy(() -> testSystem.cancelBooking(bookingList.getFirst().getId())).hasMessage("Kan inte avboka påbörjad eller avslutad bokning");
    }
}