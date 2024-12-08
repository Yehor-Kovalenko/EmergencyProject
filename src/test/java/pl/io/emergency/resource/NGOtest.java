package pl.io.emergency.resource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.io.emergency.entity.NGO;
import pl.io.emergency.entity.Role;
import pl.io.emergency.entity.User;
import pl.io.emergency.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

public class NGOtest {

    @Test
    public void testSaveEntity() {
        // Tworzymy mocka repozytorium
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        // Tworzymy przykładową encję NGO
        NGO ngo = new NGO();
        ngo.setUsername("testngo");
        ngo.setPassword("securepassword");
        ngo.setEmail("testngo@example.com");
        ngo.setPhone("123456789");
        ngo.setRole(Role.NGO);
        ngo.setName("Test NGO");
        ngo.setKrs("123456789");

        // Tworzymy obiekt encji, który ma być zwrócony po zapisaniu
        NGO savedNGO = new NGO();
        savedNGO.setId(1L); // Ustawiamy wygenerowane ID
        savedNGO.setUsername("testngo");
        savedNGO.setPassword("securepassword");
        savedNGO.setEmail("testngo@example.com");
        savedNGO.setPhone("123456789");
        savedNGO.setRole(Role.NGO);
        savedNGO.setName("Test NGO");
        savedNGO.setKrs("123456789");

        // Określamy, że po wywołaniu save() z mocka, ma zostać zwrócony savedNGO
        Mockito.when(userRepository.save(ngo)).thenReturn(savedNGO);

        // Wywołanie metody save()
        NGO result = (NGO) userRepository.save(ngo);

        // Sprawdzenie, czy ID zostało wygenerowane
        assertNotNull(result.getId(), "ID powinno zostać wygenerowane");
        assertEquals(1L, result.getId(), "ID powinno wynosić 1");

        // Sprawdzamy, czy encja NGO została zapisana i czy zwrócony obiekt to expected savedNGO
        assertEquals(savedNGO, result, "Zapisana encja powinna być zgodna z oczekiwaną");
    }

    @Test
    public void testSettersAndGetters() {
        // Tworzenie obiektu klasy NGO
        NGO ngo = new NGO();

        // Ustawianie wartości pól
        ngo.setId(2L);
        ngo.setUsername("newngo");
        ngo.setPassword("newpassword");
        ngo.setEmail("newngo@example.com");
        ngo.setPhone("987654321");
        ngo.setRole(Role.NGO);
        ngo.setName("New NGO");
        ngo.setKrs("987654321");

        // Sprawdzanie wartości pól za pomocą getterów
        assertEquals(2L, ngo.getId(), "ID powinno wynosić 2");
        assertEquals("newngo", ngo.getUsername(), "Nazwa użytkownika powinna wynosić 'newngo'");
        assertEquals("newpassword", ngo.getPassword(), "Hasło powinno wynosić 'newpassword'");
        assertEquals("newngo@example.com", ngo.getEmail(), "Email powinien wynosić 'newngo@example.com'");
        assertEquals("987654321", ngo.getPhone(), "Telefon powinien wynosić '987654321'");
        assertEquals(Role.NGO, ngo.getRole(), "Rola powinna wynosić NGO");
        assertEquals("New NGO", ngo.getName(), "Nazwa NGO powinna wynosić 'New NGO'");
        assertEquals("987654321", ngo.getKrs(), "KRS powinien wynosić '987654321'");
    }

    @Test
    public void testInheritance() {
        // Tworzymy obiekt NGO
        NGO ngo = new NGO();

        // Sprawdzamy, czy NGO jest instancją User
        assertTrue(ngo instanceof User, "NGO powinno dziedziczyć po User");
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
