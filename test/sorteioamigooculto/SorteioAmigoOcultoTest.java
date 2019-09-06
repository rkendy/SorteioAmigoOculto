/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sorteioamigooculto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rkendy
 */
public class SorteioAmigoOcultoTest {
    
    private SorteioAmigoOculto sorteio;
    
    public SorteioAmigoOcultoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.sorteio = new SorteioAmigoOculto();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class SorteioAmigoOculto.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        SorteioAmigoOculto.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
//    public void testCarregaParticipantes() {
//        this.sorteio.carregaParticipantes();
//        assertEquals(this.sorteio.);
//    }
    
}
