package com.MyCAlerts.factory;

import com.MyCAlerts.dataProvider.ConfigDataProvider;
import com.MyCAlerts.dataProvider.ExcelDataProvider;

public class DataProviderFactory {

	public static ConfigDataProvider getConfig() {
		ConfigDataProvider config = new ConfigDataProvider();
		return config;
	}

	public static ExcelDataProvider getExcel() {
		ExcelDataProvider excel = new ExcelDataProvider();
		return excel;
	}

	
	
}
