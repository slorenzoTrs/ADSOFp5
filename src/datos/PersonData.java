package datos;

/**
 * Clase de ejemplo para representar a una persona con atributos modificables.
 */
public class PersonData implements PublicCloneable<PersonData> {
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
	
	@Override
	public PersonData clone() {
        try {
			return (PersonData) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}
