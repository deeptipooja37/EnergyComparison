package dshukla.EnergyComparison;

import java.util.List;

public class Plan {
	private String supplier;
	private String plan;
	private List<Rate> rates;
	private double standingCharges;
	
	public double getStandingCharges() {
		return standingCharges;
	}
	public void setStandingCharges(double standingCharges) {
		this.standingCharges = standingCharges;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
	}
	public List<Rate> getRates() {
		return rates;
	}
	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
	
}
class Rate{
	private double price;
	private int threshold;
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
}
