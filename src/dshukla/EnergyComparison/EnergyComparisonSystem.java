package dshukla.EnergyComparison;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class EnergyComparisonSystem {

	private List<Plan> plansList;
	public EnergyComparisonSystem(Object plans) {
		plansList = new ArrayList<Plan>();
		
		if(plans instanceof JSONArray){
			JSONArray plansArray = (JSONArray)plans;
			
			for(Object o : plansArray){
				if(o instanceof JSONObject){
					Plan plan = new Plan();

					JSONObject json = (JSONObject) o;
					plan.setSupplier((String)json.get("supplier")); 
					plan.setPlan((String)json.get("plan"));
					
					Object standingChargeObj = json.get("standing_charge");
					if(standingChargeObj!=null){
						Double standingCharge = ((Number)standingChargeObj).doubleValue();
						plan.setStandingCharges(standingCharge);
					}
					
					Object rates = json.get("rates");
					List<Rate> rateList = new ArrayList<>();

					if(rates instanceof JSONArray){
						JSONArray ratesArray = (JSONArray)rates;
						rateList = new ArrayList<>();
						
						for(Object rate : ratesArray){
							Rate r = new Rate();
							
							if(rate instanceof JSONObject){
								JSONObject ratejson = (JSONObject) rate;

								Object priceObj= ratejson.get("price");
								double price = priceObj!=null ? ((Number)priceObj).doubleValue() : 0;
								r.setPrice(price);
								
								Object threshold= ratejson.get("threshold");
								int numthresold = threshold!=null ? ((Number)threshold).intValue() : 0;
								r.setThreshold(numthresold);
								rateList.add(r);
							}
						}
					}
					
					plan.setRates(rateList); 
					
					
					plansList.add(plan);
				}
			}
		}else{
			System.out.println("WRONG JSON Structure");
		}
	}
	public List<Plan> getPlansList() {
		return plansList;
	}
	public void setPlansList(List<Plan> plansList) {
		this.plansList = plansList;
	}
	public void processPriceCommand(int annual_Usage) {
		List<ResultModel> result = new ArrayList<>();
		
		for (int i = 0; i < plansList.size(); i++) {
			Plan p = plansList.get(i);
			double totalCost =0;
			int annualUsage = annual_Usage;
			for (int j = 0; j < p.getRates().size() && annualUsage>0; j++) {

				Rate r = p.getRates().get(j);
				int thresold = r.getThreshold();
				double price = r.getPrice();
				
				if (thresold>0 && annualUsage > thresold) {
					totalCost = totalCost + thresold*price;
					annualUsage= annualUsage - thresold;
				} else {
					totalCost = totalCost + annualUsage*price;
					annualUsage= 0;
				}
			}
			
			totalCost = totalCost + p.getStandingCharges()*365;
			totalCost = totalCost + totalCost*0.05;
			totalCost = totalCost/100;

			totalCost = totalCost*100;
			totalCost = Math.round(totalCost);
			totalCost = totalCost/100;
			result.add(new ResultModel(p.getSupplier(), p.getPlan(), totalCost));
			
		}
		Collections.sort(result, new Comparator<ResultModel>() {

			@Override
			public int compare(ResultModel o1, ResultModel o2) {
				if (o1.getTotalCost() < o2.getTotalCost()) {
					return -1;
				} else if (o1.getTotalCost() > o2.getTotalCost()) {
					return 1;
				}
				return 0;
			}
		});

		for (Iterator iterator = result.iterator(); iterator.hasNext();) {
			ResultModel result_TotalCost = (ResultModel) iterator.next();
			System.out.println(result_TotalCost.getSupplier()+","+result_TotalCost.getPlan()+","+ result_TotalCost.getTotalCost());
		}
	}
	
	public void processUsageCommand(String input) {
		String supplier = input.split(" ")[1];
		// money spent in pounds
		double monthlySpend =  Double.parseDouble(input.split(" ")[3]);
		double consumption = 0;
				
		// find plan 
		Plan p = null;
		for (Iterator iterator = plansList.iterator(); iterator.hasNext();) {
			Plan plan = (Plan) iterator.next();
			if (plan.getSupplier().equals(supplier)) {
				p=plan;
			}
		}
		// convert standing charges to pence for year
		monthlySpend = monthlySpend*100*12;
		
		// remove VAT
		monthlySpend = monthlySpend/1.05;
		
		// if plan has standing charges
		if(p.getStandingCharges()>0){
			double standingCharges = p.getStandingCharges()*365;
			monthlySpend = monthlySpend- standingCharges;
		}
		
		// finds energy used annually 
		for (Iterator iterator = p.getRates().iterator(); iterator.hasNext() && monthlySpend>0;) {
			Rate r = (Rate) iterator.next();
			int thresold = r.getThreshold();
			double price = r.getPrice();
			if (thresold>0) {
				double totalCost = thresold*price;
				if (totalCost < monthlySpend) {
					consumption  = consumption+ thresold;
					monthlySpend = monthlySpend - thresold*price;
				}else{
					consumption = consumption + monthlySpend/price;
					monthlySpend =0;
				}
			} else {
				consumption = consumption + monthlySpend/price;
				monthlySpend=0;
			}
		}
		
		System.out.println((int)consumption);
	}

}
class ResultModel {

	public ResultModel(String supplier, String plan, double totalCost) {
		super();
		this.supplier = supplier;
		this.plan = plan;
		this.totalCost = totalCost;
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
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	private String supplier;
	private String plan;
	private double totalCost;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plan == null) ? 0 : plan.hashCode());
		result = prime * result + ((supplier == null) ? 0 : supplier.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalCost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultModel other = (ResultModel) obj;
		if (plan == null) {
			if (other.plan != null)
				return false;
		} else if (!plan.equals(other.plan))
			return false;
		if (supplier == null) {
			if (other.supplier != null)
				return false;
		} else if (!supplier.equals(other.supplier))
			return false;
		if (Double.doubleToLongBits(totalCost) != Double.doubleToLongBits(other.totalCost))
			return false;
		return true;
	}
	
}

