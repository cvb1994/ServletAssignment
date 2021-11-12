package model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pondmanager")
public class Pond {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "pondName", nullable = false)
	private String pondName;

	@Column(name = "address", nullable = true)
	private String pondAddress;

	@Column(name = "phone", nullable = true)
	private String phone;

	@Column(name = "amountOfType", columnDefinition = "integer default 0")
	private int amountOfType;

	@Column(name = "amountOfTotal", columnDefinition = "integer default 0")
	private int amountOfTotal;

	@OneToMany(mappedBy="pond")
	private List<Fish> fishes;
	
	public Pond() {}

	public Pond(String pondName, String pondAddress, String phone) {
		this.pondName = pondName;
		this.pondAddress = pondAddress;
		this.phone = phone;
	}

	public Pond(int id, String pondName, String pondAddress, String phone) {
		this.id = id;
		this.pondName = pondName;
		this.pondAddress = pondAddress;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPondName() {
		return pondName;
	}

	public void setPondName(String pondName) {
		this.pondName = pondName;
	}

	public String getPondAddress() {
		return pondAddress;
	}

	public void setPondAddress(String pondAddress) {
		this.pondAddress = pondAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAmountOfType() {
		return amountOfType;
	}

	public void setAmountOfType(int amountOfType) {
		this.amountOfType = amountOfType;
	}

	public int getAmountOfTotal() {
		return amountOfTotal;
	}

	public void setAmountOfTotal(int amountOfTotal) {
		this.amountOfTotal = amountOfTotal;
	}
}
