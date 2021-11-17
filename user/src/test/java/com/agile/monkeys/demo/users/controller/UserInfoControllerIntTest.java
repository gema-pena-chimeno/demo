package com.agile.monkeys.demo.users.controller;

import com.agile.monkeys.demo.data.UserInfo;
import com.agile.monkeys.demo.data.UserRole;
import com.agile.monkeys.demo.users.SpringBase;
import com.agile.monkeys.demo.users.domain.UserInfoRepository;
import com.agile.monkeys.demo.users.service.LastAdminException;
import com.agile.monkeys.demo.users.service.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.agile.monkeys.demo.data.UserRole.ADMIN;
import static com.agile.monkeys.demo.data.UserRole.USER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Note that this test cannot test:
 * - Spring security configuration to set access over pages.
 * - Format validation on the parameters of the REST API.
 */
@RunWith(SpringRunner.class)
public class UserInfoControllerIntTest extends SpringBase {

    @Autowired
    private UserController userController;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void create_withAdminRole_userCreated() {
        // given
        UserRole role = ADMIN;
        String userName = "user-to-create-1";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);

        // when
        final UserDto result = userController.create(dto);

        // then
        assertUserDto(result, userName, password, role);
        assertUserInDb(result.getId(), userName, password, role, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void create_sameUserTwoTimes_onlyFirstIsCreated() {
        // given
        UserRole role = USER;
        String userName = "user-to-create-2";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        userController.create(dto);

        // when / then
        assertThrows(DataIntegrityViolationException.class, () -> userController.create(dto));
    }
    
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void update_changePasswordAndRole_passwordUpdated() {
        // given
        UserRole role = USER;
        String userName = "user-to-update-1";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        final UserDto createdUser = userController.create(dto);

        UserRole newRole = ADMIN;
        String newPassword = "12345";
        CRUDDto updateDto = generateCRUDDto(userName, newPassword, newRole);

        // when
        final UserDto result = userController.update(createdUser.getId(), updateDto);

        // then
        assertUserDto(result, userName, newPassword, newRole);
        assertUserInDb(result.getId(), userName, newPassword, newRole, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void update_changeUserName_changeIgnored() {
        // given
        UserRole role = USER;
        String userName = "user-to-update-2";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        final UserDto createdUser = userController.create(dto);

        String newUserName = "user-to-update-3";
        CRUDDto updateDto = generateCRUDDto(newUserName, password, role);

        // when
        final UserDto result = userController.update(createdUser.getId(), updateDto);

        // then
        assertUserDto(result, userName, password, role);
        assertUserInDb(result.getId(), userName, password, role, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void update_notExistingUser_notFoundException() {
        // given
        UserRole role= ADMIN;
        String userName = "user-to-update-4";
        String password = "1234";
        CRUDDto updateDto = generateCRUDDto(userName, password, role);

        // when / then
        assertThrows(NotFoundException.class, () ->
                userController.update("unexisting-user-id", updateDto));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updateRole_fromUserToAdmin_changed() {
        // given
        UserRole role= USER;
        String userName = "user-to-update-role-1";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        final UserDto createdUser = userController.create(dto);

        // when
        UserDto result = userController.updateRole(createdUser.getId(), ADMIN);

        // then
        assertUserDto(result, userName, password, ADMIN);
        assertUserInDb(result.getId(), userName, password, ADMIN, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updateRole_fromAdminToUser_changed() {
        // given
        UserRole role= ADMIN;
        String userName = "user-to-update-role-2";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        final UserDto createdUser = userController.create(dto);

        // when
        UserDto result = userController.updateRole(createdUser.getId(), USER);

        // then
        assertUserDto(result, userName, password, USER);
        assertUserInDb(result.getId(), userName, password, USER, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updateRole_fromAdminToAdmin_nothingChange() {
        // given
        UserRole role= ADMIN;
        String userName = "user-to-update-role-3";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        final UserDto createdUser = userController.create(dto);

        // when
        UserDto result = userController.updateRole(createdUser.getId(), ADMIN);

        // then
        assertUserDto(result, userName, password, role);
        assertUserInDb(result.getId(), userName, password, ADMIN, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updateRole_fromUserToUser_nothingChange() {
        // given
        UserRole role= USER;
        String userName = "user-to-update-role-4";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        final UserDto createdUser = userController.create(dto);

        // when
        UserDto result = userController.updateRole(createdUser.getId(), USER);

        // then
        assertUserDto(result, userName, password, role);
        assertUserInDb(result.getId(), userName, password, USER, true);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void updateRole_fromAdminToUserUntilLastAdmin_lastAdminNotChanged() {
        // given (at least 2 adminw)
        UserRole role= ADMIN;
        String password = "1234";
        userController.create(generateCRUDDto("admin-1", password, role));
        userController.create(generateCRUDDto("admin-2", password, role));

        List<UserInfo> allAdmins = userInfoRepository.findAll()
                .stream()
                .filter(userInfo -> userInfo.isActive() && userInfo.getRole().equals(ADMIN.toString()))
                .collect(Collectors.toList());
        assertTrue(allAdmins.size() > 0);

        UserInfo lastAdmin = allAdmins.remove(0);
        for (UserInfo admin : allAdmins) {
            userController.updateRole(admin.getId(), USER);
        }

        // when / then
        assertThrows(LastAdminException.class, () ->
                userController.updateRole(lastAdmin.getId(), USER));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void delete_existingUser_userInactive() {
        // given
        UserRole role = USER;
        String userName = "user-to-delete-1";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        final UserDto createdUser = userController.create(dto);

        // when
        userController.delete(createdUser.getId());

        // then
        assertUserInDb(createdUser.getId(), userName, password, role, false);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void delete_notExistingUser_notFoundException() {
        // when / then
        assertThrows(NotFoundException.class, () -> userController.delete("unexisting-user-id"));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void getUser_existingUser_userReturned() {
        // given
        UserRole role = USER;
        String userName = "user-to-find-1";
        String password = "1234";
        CRUDDto dto = generateCRUDDto(userName, password, role);
        final UserDto createdUser = userController.create(dto);
        assertUserInDb(createdUser.getId(), userName, password, role, true);

        // when
        UserDto result = userController.getUser(createdUser.getId());

        // then
        assertUserDto(result, userName, password, role);
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void getUser_nonExistingUser_notFoundException() {
        // when / then
        assertThrows(NotFoundException.class, () -> userController.getUser("unexisting-user-id"));
    }
    
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void list_existingUsers_usersListed() {
        // given
        UserRole role1 = USER;
        String userName1 = "user-to-list-1";
        String password1 = "password";
        CRUDDto dto1 = generateCRUDDto(userName1, password1, role1);
        final UserDto createdUser1 = userController.create(dto1);
        UserRole role2 = USER;
        String userName2 = "user-to-list-2";
        String password2 = "password";
        CRUDDto dto2 = generateCRUDDto(userName2, password2, role2);
        final UserDto createdUser2 = userController.create(dto2);
        UserRole role3 = ADMIN;
        String userName3 = "user-to-list-3";
        String password3 = "password";
        CRUDDto dto3 = generateCRUDDto(userName3, password3, role3);
        final UserDto createdUser3 = userController.create(dto3);
        userController.delete(createdUser3.getId());

        // when
        List<UserDto> result = userController.list();

        // then
        List<String> returnedIds = result.stream().map(UserDto::getId).collect(Collectors.toList());
        assertTrue(returnedIds.containsAll(List.of(createdUser1.getId(), createdUser2.getId())));
        assertFalse(returnedIds.contains(createdUser3.getId()));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void search_foundByQuery_usersReturned() {
        // given
        UserRole role = USER;
        CRUDDto dto1 = generateCRUDDto("Peter2002", "1234", USER);
        userController.create(dto1);
        CRUDDto dto2 = generateCRUDDto("peter.smith", "1234", USER);
        userController.create(dto2);
        CRUDDto dto3 = generateCRUDDto("olga.harris", "1234", ADMIN);
        userController.create(dto3);
        CRUDDto dto4 = generateCRUDDto("harry-1980", "1234", ADMIN);
        userController.create(dto4);

        CRUDDto dto5 = generateCRUDDto("peter.fox.1974", "1234", USER);
        final UserDto createdUser = userController.create(dto5);
        userController.delete(createdUser.getId());

        // when/ then
        List<UserDto> result1 = userController.search("peter");
        assertEquals(3, result1.size());

        List<UserDto> result2 = userController.search("harr");
        assertEquals(2, result2.size());

        List<UserDto> result3 = userController.search("fox");
        assertEquals(1, result3.size());

        List<UserDto> result4 = userController.search("Smi");
        assertEquals(1, result4.size());
    }

    private CRUDDto generateCRUDDto(String userName, String password, UserRole role) {
        CRUDDto dto = new CRUDDto();
        dto.setUserName(userName);
        dto.setPassword(password);
        dto.setRole(role);

        return dto;
    }

    private void assertUserDto(UserDto userDto, String userName, String password, UserRole role) {
        assertAll(
                () -> assertNotNull(userDto),
                () -> assertEquals(userName, userDto.getUserName()),
                () -> assertEquals(role, userDto.getRole()));
    }

    private void assertUserInDb(String id,
                                String userName,
                                String password,
                                UserRole role,
                                boolean active) {

        Optional<UserInfo> userInfo = userInfoRepository.findById(id);
        assertAll(
                () -> assertTrue(userInfo.isPresent()),
                () -> assertEquals(userName, userInfo.get().getUserName()),
                () -> assertEquals(password, userInfo.get().getPassword()),
                () -> assertEquals(role.toString(), userInfo.get().getRole()),
                () -> assertEquals(active, userInfo.get().isActive()));
    }
}


