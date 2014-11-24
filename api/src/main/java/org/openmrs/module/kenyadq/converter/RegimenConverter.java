package org.openmrs.module.kenyadq.converter;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.ConceptName;
import org.openmrs.DrugOrder;
import org.openmrs.Obs;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.CoreConstants;
import org.openmrs.module.kenyadq.utils.RDQAReportUtils;
import org.openmrs.module.kenyaemr.regimen.RegimenOrder;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.util.OpenmrsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter to get obsDatetime from an observation
 */
public class RegimenConverter implements DataConverter {
	@Override
	public Object convert(Object original) {
		SimpleResult sr = (SimpleResult) original;

		if (sr == null)
			return "Missing";

		RegimenOrder ro = (RegimenOrder) sr.getValue();

		if (CollectionUtils.isEmpty(ro.getDrugOrders())) {
			return "Missing";
		}
		List<String> components = new ArrayList<String>();

		for (DrugOrder o : ro.getDrugOrders()) {
			ConceptName cn = o.getConcept().getShortNameInLocale(CoreConstants.LOCALE);
			if (cn == null) {
				cn = o.getConcept().getShortNameInLocale(CoreConstants.LOCALE);
			}
			components.add(cn.getName());
		}

		return OpenmrsUtil.join(components, " + ");

	}

	@Override
	public Class<?> getInputDataType() {
		return SimpleResult.class;
	}

	@Override
	public Class<?> getDataType() {
		return String.class;
	}

	private String getStandardRegimen(String ...drugs){return null;}
}
