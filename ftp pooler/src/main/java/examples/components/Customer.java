package examples.components;

import java.util.Random;

public class Customer {

	private OrderManager orderManager;

	public Customer(OrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public void placeOrder() {
		CoffeeOrder coffeeOrder = generateRandomCoffeeOrder();
		orderManager.send(coffeeOrder);
	}

	private CoffeeOrder generateRandomCoffeeOrder(){
		Random random = new Random();
		boolean milk = random.nextLong() < 0.5;
		boolean sugar = random.nextLong() < 0.5;
		return new CoffeeOrder(milk, sugar);
	}
}
