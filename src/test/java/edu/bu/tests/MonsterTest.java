package edu.bu.tests;
import edu.bu.model.entitities.Monster;
import edu.bu.model.entitities.Player;
import edu.bu.model.items.*;
import edu.bu.util.Die;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {

    private Monster monster;
    private Weapon mockWeapon;
    private Armor mockArmor;
    private Player mockPlayer;

    @BeforeEach
    public void setUp() {
        mockWeapon = Mockito.mock(Weapon.class);
        mockArmor = Mockito.mock(Armor.class);
        mockPlayer = Mockito.mock(Player.class);

        Mockito.when(mockWeapon.getAttackRating()).thenReturn(5);
        Mockito.when(mockArmor.getDefenseRating()).thenReturn(5);

        monster = new Monster(
                "TestMonster",
                "A scary monster",
                10,
                mockWeapon,
                mockArmor,
                new ArrayList<>()
        );

        // Ensure the Die instance in Monster is properly set up
        monster.attackDie = new Die(20); // Default to a 20-sided die
    }

    @Test
    public void testAttackSuccess() {
        Mockito.when(mockPlayer.getDefenseRating()).thenReturn(3);

        // Ensure the Die roll will be high enough to hit the player
        monster.attackDie = new Die(20) {
            @Override
            public int rollDie() {
                return 20; // Return max value to ensure a hit
            }
        };

        monster.attack(mockPlayer);

        Mockito.verify(mockPlayer, Mockito.times(1)).takeDamage(ArgumentMatchers.anyInt());
    }

    @Test
    public void testAttackFailure() {
        Mockito.when(mockPlayer.getDefenseRating()).thenReturn(10);

        // Ensure the Die roll will be low enough to miss the player
        monster.attackDie = new Die(20) {
            @Override
            public int rollDie() {
                return 1; // Return min value to ensure a miss
            }
        };

        monster.attack(mockPlayer);

        Mockito.verify(mockPlayer, Mockito.never()).takeDamage(ArgumentMatchers.anyInt());
    }

    @Test
    public void testTakeDamage() {
        monster.takeDamage(5);
        assertEquals(5, monster.getHealth());
        assertTrue(monster.isAlive());

        monster.takeDamage(5);
        assertEquals(0, monster.getHealth());
        assertFalse(monster.isAlive());
    }

    @Test
    public void testGettersAndSetters() {
        monster.setHealth(20);
        assertEquals(20, monster.getHealth());

        monster.setEquippedWeapon(mockWeapon);
        assertEquals(mockWeapon, monster.getEquippedWeapon());

        monster.setEquippedArmor(mockArmor);
        assertEquals(mockArmor, monster.getEquippedArmor());

        monster.setAttackRating(10);
        assertEquals(10, monster.getAttackRating());

        monster.setDefenseRating(8);
        assertEquals(8, monster.getDefenseRating());

        monster.setAlive(false);
        assertFalse(monster.isAlive());
    }
}
