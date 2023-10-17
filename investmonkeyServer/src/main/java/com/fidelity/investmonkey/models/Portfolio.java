package com.fidelity.investmonkey.models;

import java.util.List;
import java.util.Objects;


public class Portfolio {
	private List<Holdings> holdings;
	private ChartData chartData;
	
	public Portfolio(List<Holdings> holdings) {
		super();
		this.holdings = holdings;
	}
	public List<Holdings> getHoldings() {
		return holdings;
	}
	public void setHoldings(List<Holdings> holdings) {
		this.holdings = holdings;
	}
	public ChartData getChartData() {
		return chartData;
	}
	public void setChartData(ChartData chartData) {
		this.chartData = chartData;
	}
	@Override
	public int hashCode() {
		return Objects.hash(chartData, holdings);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Portfolio other = (Portfolio) obj;
		return Objects.equals(chartData, other.chartData) && Objects.equals(holdings, other.holdings);
	}
	@Override
	public String toString() {
		return "Portfolio [holdings=" + holdings + ", chartData=" + chartData + "]";
	}
	
	
}
