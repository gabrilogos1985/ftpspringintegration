package examples.components;

public class Barista {

	private String name;

	public Barista(String name) {
		this.name = name;
	}

	public void makeCoffee(Object request) {
		System.out.println(String.format("%s making coffee order [%s]", name,
				request.toString()));
	}

}
