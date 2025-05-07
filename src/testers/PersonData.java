package testers;

/**
 * Clase de ejemplo para representar a una persona con atributos modificables.
 */
class PersonData {
	public String name;
	public int age;

	public PersonData(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return name + ", " + age + " años";
	}
}
