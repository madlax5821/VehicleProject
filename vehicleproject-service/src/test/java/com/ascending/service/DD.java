//package com.ascending.pattern;
//​
//import com.ascending.init.AppInitializer;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Captor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//​
//import org.mockito.Mockito;
//​
//import static com.ascending.training.pattern.Pizza.PizzaBuilder;
//​
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//​
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= AppInitializer.class)
//public class PizzaServiceMockTest {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//​
//    private Pizza expectedPizza;
//​
//    @Captor
//    ArgumentCaptor<String> stringCaptor;
//​
//    @Before
//    public void setup() {
//        expectedPizza = new Pizza
//                .PizzaBuilder()
//                .name("Cheese")
//                .size(12)
//                .extraTopping("Mushroom")
//                .stuffedCrust(false)
//                .collect(true)
//                .discount(20)
//                .build();
//    }
//​
//    @Test
//    public void givenTraditonalMocking_whenServiceInvoked_thenPizzaIsBuilt() {
//        Pizza.PizzaBuilder nameBuilder = mock(Pizza.PizzaBuilder.class);
//        Pizza.PizzaBuilder sizeBuilder = mock(Pizza.PizzaBuilder.class);
//        Pizza.PizzaBuilder firstToppingBuilder = mock(Pizza.PizzaBuilder.class);
//        Pizza.PizzaBuilder secondToppingBuilder = mock(Pizza.PizzaBuilder.class);
//        Pizza.PizzaBuilder stuffedBuilder = mock(Pizza.PizzaBuilder.class);
//        Pizza.PizzaBuilder willCollectBuilder = mock(Pizza.PizzaBuilder.class);
//        Pizza.PizzaBuilder discountBuilder = mock(Pizza.PizzaBuilder.class);
//​
//        Pizza.PizzaBuilder builder = mock(Pizza.PizzaBuilder.class);
//​
//        when(builder.name(anyString())).thenReturn(nameBuilder);
//        when(nameBuilder.size(anyInt())).thenReturn(sizeBuilder);
//        when(sizeBuilder.extraTopping(anyString())).thenReturn(firstToppingBuilder);
//        when(firstToppingBuilder.stuffedCrust(anyBoolean())).thenReturn(stuffedBuilder);
//        when(stuffedBuilder.extraTopping(anyString())).thenReturn(secondToppingBuilder);
//        when(secondToppingBuilder.collect(anyBoolean())).thenReturn(willCollectBuilder);
//        when(willCollectBuilder.discount(anyInt())).thenReturn(discountBuilder);
//        when(discountBuilder.build()).thenReturn(expectedPizza);
//​
//        PizzaService pizzaService = new PizzaService(builder);
//        Pizza pizza = pizzaService.orderHouseSpecial();
//        assertEquals("Expected Pizza", expectedPizza, pizza);
//​
//        verify(builder).name(stringCaptor.capture());
//        assertEquals("Pizza name: ", "Special", stringCaptor.getValue());
//​
//        // rest of test verification
//        verify(builder, times(1)).name(anyString());
//        verify(nameBuilder, times(1)).size(anyInt());
//        verify(sizeBuilder, times(1)).extraTopping(anyString());
//        verify(firstToppingBuilder, times(1)).stuffedCrust(anyBoolean());
//        verify(stuffedBuilder, times(1)).extraTopping(anyString());
//    }
//​
//    @Test
//    public void givenDeepMocks_whenServiceInvoked_thenPizzaIsBuilt() {
//        PizzaBuilder builder = mock(Pizza.PizzaBuilder.class, Mockito.RETURNS_DEEP_STUBS);
//​
//        when(builder.name(anyString())
//                .size(anyInt())
//                .extraTopping(anyString())
//                .stuffedCrust(anyBoolean())
//                .extraTopping(anyString())
//                .collect(anyBoolean())
//                .discount(anyInt())
//                .build())
//                .thenReturn(expectedPizza);
//​
//        PizzaService service = new PizzaService(builder);
//        Pizza pizza = service.orderHouseSpecial();
//        assertEquals("Expected Pizza", expectedPizza, pizza);
//    }
//}