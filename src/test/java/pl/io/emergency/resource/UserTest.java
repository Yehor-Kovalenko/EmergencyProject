package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.io.emergency.entity.Role;
import pl.io.emergency.entity.User;
import pl.io.emergency.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testSaveEntity() {
        // Tworzymy mocka repozytorium
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        // Tworzymy przykładową encję
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("securepassword");
        user.setEmail("testuser@example.com");
        user.setPhone("123456789");
        user.setRole(Role.VOLUNTEER);

        // Tworzymy obiekt encji, który ma być zwrócony po zapisaniu
        User savedUser = new User();
        savedUser.setId(1L);  // Ustawiamy wygenerowane ID
        savedUser.setUsername("testuser");
        savedUser.setPassword("securepassword");
        savedUser.setEmail("testuser@example.com");
        savedUser.setPhone("123456789");
        savedUser.setRole(Role.VOLUNTEER);

        // Określamy, że po wywołaniu save() z mocka, ma zostać zwrócony savedUser
        Mockito.when(userRepository.save(user)).thenReturn(savedUser);

        // Wywołanie metody save()
        User result = userRepository.save(user);

        // Sprawdzenie, czy ID zostało wygenerowane
        assertNotNull(result.getId(), "ID powinno zostać wygenerowane");
        assertEquals(1L, result.getId(), "ID powinno wynosić 1");

        // Sprawdzamy, czy encja została zapisana i czy zwrócony obiekt to expected savedUser
        assertEquals(savedUser, result, "Zapisana encja powinna być zgodna z oczekiwaną");
    }

    @Test
    public void testSettersAndGetters() {
        // Tworzenie obiektu klasy User
        User user = new User();

        // Ustawianie wartości pól
        user.setId(2L);
        user.setUsername("newuser");
        user.setPassword("newpassword");
        user.setEmail("newuser@example.com");
        user.setPhone("987654321");
        user.setRole(Role.NGO);

        // Sprawdzanie wartości pól za pomocą getterów
        assertEquals(2L, user.getId(), "ID powinno wynosić 2");
        assertEquals("newuser", user.getUsername(), "Nazwa użytkownika powinna wynosić 'newuser'");
        assertEquals("newpassword", user.getPassword(), "Hasło powinno wynosić 'newpassword'");
        assertEquals("newuser@example.com", user.getEmail(), "Email powinien wynosić 'newuser@example.com'");
        assertEquals("987654321", user.getPhone(), "Telefon powinien wynosić '987654321'");
        assertEquals(Role.NGO, user.getRole(), "Rola powinna wynosić NGO");
    }

    @Test
    public void testRoleEnumValues() {
        // Sprawdzamy wartości enum Role
        Role[] roles = Role.values();

        assertEquals(4, roles.length, "Role powinny zawierać 4 wartości");
        assertEquals(Role.VOLUNTEER, roles[0], "Pierwsza rola powinna wynosić VOLUNTEER");
        assertEquals(Role.GIVER, roles[1], "Druga rola powinna wynosić GIVER");
        assertEquals(Role.NGO, roles[2], "Trzecia rola powinna wynosić NGO");
        assertEquals(Role.OFFICIAL, roles[3], "Czwarta rola powinna wynosić OFFICIAL");
    }

    @Test
    public void testRoleEnumValueOf() {
        // Sprawdzamy, czy Role.valueOf zwraca odpowiednie wartości
        assertEquals(Role.VOLUNTEER, Role.valueOf("VOLUNTEER"));
        assertEquals(Role.GIVER, Role.valueOf("GIVER"));
        assertEquals(Role.NGO, Role.valueOf("NGO"));
        assertEquals(Role.OFFICIAL, Role.valueOf("OFFICIAL"));
    }
}
