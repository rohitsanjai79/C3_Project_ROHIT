import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    public void initalSettingUp(){
        restaurant = new Restaurant("Amelie's cafe","chennai",LocalTime.parse("10:00:00"),LocalTime.parse("23:00:00"));
        service.addRestaurant("Amelie's cafe","chennai",LocalTime.parse("10:00:00"),LocalTime.parse("23:00:00"));
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        initalSettingUp();
        assertEquals(restaurant.getName(),service.findRestaurantByName("Amelie's cafe").getName());
    }

    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        initalSettingUp();
        assertThrows(restaurantNotFoundException.class,()->{service.findRestaurantByName("ramu's cafe");});
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>
    //>>>>>>>>>>>>>>>>>>>>>>TOTAL COST CALCULATION<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void when_individual_order_is_selected_then_the_total_of_order_should_be_returned(){
        initalSettingUp();
        List<Item> itemsSelected = restaurant.getMenu();
        assertEquals(388,restaurant.orderTotalCost(itemsSelected));
    }
    @Test
    public void when_individual_item_from_order_is_removed_recalculate_total_of_order_should_be_returned(){
        initalSettingUp();
        List<Item> itemsSelected = restaurant.getMenu();
        itemsSelected.remove(1);
        assertEquals(119,restaurant.orderTotalCost(itemsSelected));
    }
    //<<<<<<<<<<<<<<<<<<<<TOTAL COST CALCULATION>>>>>>>>>>>>>>>>>>>>>>>>>>
    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        initalSettingUp();
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        initalSettingUp();
        assertThrows(restaurantNotFoundException.class,()->{service.removeRestaurant("Pantry d'or");});
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        initalSettingUp();
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>
}