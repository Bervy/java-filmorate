package ru.yandex.practicum.filmorate.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.repository.FriendshipDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "classpath:data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FriendshipStorageTests {
    private final FriendshipDao friendshipDao;

    @Autowired
    public FriendshipStorageTests(@Qualifier("friendshipImpl") FriendshipDao friendshipDao) {
        this.friendshipDao = friendshipDao;
    }

    @Test
    void shouldAddFriendAndRemoveFriend() {
        friendshipDao.addFriend(1, 2);
        assertThat(friendshipDao.getFriends(1).size()).isEqualTo(1);
        friendshipDao.deleteFriend(1, 2);
        assertThat(friendshipDao.getFriends(1).size()).isZero();
    }

    @Test
    void shouldFindUserFriends() {
        friendshipDao.addFriend(1L, 2L);
        friendshipDao.addFriend(1L, 3L);
        assertThat(friendshipDao.getFriends(1).size()).isEqualTo(2);
    }

    @Test
    void shouldFindCommonFriends() {
        friendshipDao.addFriend(1L, 3L);
        friendshipDao.addFriend(2L, 3L);
        List<User> commonFriends = friendshipDao.getCommonFriends(1, 2);
        assertThat(commonFriends).hasSize(1);
    }
}