package model;

import javax.persistence.*;

@Entity
@Table(name = "fishmanager")
public class Fish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "fishName", nullable = false)
	private String name;

	@Column(name = "minsize", nullable = true)
	private float min_size;

	@Column(name = "maxsize", nullable = true)
	private float max_size;

	@Column(name = "image", nullable = true)
	private String image;

	@ManyToOne
	@JoinColumn(name="pondId", nullable=false)
	private Pond pond;

	@Column(name = "amount", nullable = false)
	private int amount;

	public Fish(int id, String name, float min_size, float max_size, String image, Pond pond, int amount) {
		this.id = id;
		this.name = name;
		this.min_size = min_size;
		this.max_size = max_size;
		this.image = image;
		this.pond = pond;
		this.amount = amount;
	}

	public Fish() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMin_size() {
		return min_size;
	}

	public void setMin_size(float min_size) {
		this.min_size = min_size;
	}

	public float getMax_size() {
		return max_size;
	}

	public void setMax_size(float max_size) {
		this.max_size = max_size;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Pond getPond() {
		return pond;
	}

	public void setPond(Pond pond) {
		this.pond = pond;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
