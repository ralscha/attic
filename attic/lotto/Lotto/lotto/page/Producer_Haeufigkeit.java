package lotto.page;

import lotto.*;
import lotto.statistik.*;
import lotto.util.*;
import java.io.*;
import java.util.*;
import common.io.*;


//Properties
//byHaeufigkeit   true/false

public class Producer_Haeufigkeit extends PageProducer {

	private static final String[] templates = {"Hhalle.template", "Hh42.template", "Hh45.template", "Hh97.template",
													  "Hzalle.template", "Hz42.template", "Hz45.template", "Hz97.template"};
	private static final String[] htmlFiles = {"Hhalle.shtml", "Hh42.shtml", "Hh45.shtml", "Hh97.shtml",
													  "Hzalle.shtml", "Hz42.shtml", "Hz45.shtml", "Hz97.shtml"};


	public void producePage(Ausspielungen auss, Properties props) {
		try {
			boolean orderByHaeufigkeit = true;
			if (props != null) 
				orderByHaeufigkeit = ((Boolean)props.get("orderByHaeufigkeit")).booleanValue();
	
			java.util.List tmpList[] = new ArrayList[4];
			int haeuf[][] = new int[4][45];

			haeuf[0] = auss.getAusspielungenStatistik(AusspielungenType.ALLE).getHaeufigkeit();
			haeuf[1] = auss.getAusspielungenStatistik(AusspielungenType.AUS42).getHaeufigkeit();
			haeuf[2] = auss.getAusspielungenStatistik(AusspielungenType.AUS45).getHaeufigkeit();
			haeuf[3] = auss.getAusspielungenStatistik(AusspielungenType.AB1997).getHaeufigkeit();

			if (orderByHaeufigkeit) {
				for (int n = 0; n < 4; n++) {
					tmpList[n] = new ArrayList();
					for (int x = 0; x < 45; x++) {
						tmpList[n].add(new Entry(x + 1, haeuf[n][x]));
					}
					Collections.sort(tmpList[n]);
				}
			}

			for (int i = 0; i < 4; i++) {

				TemplateWriter tw = new TemplateWriter();

				if (orderByHaeufigkeit) {
					tw.addFile(templatePath + templates[i]);

					for (int j = 0; j < 22; j++) {
						tw.addVariable("z"+(j + 1), ((Entry) tmpList[i].get(j)).getValue());
						tw.addVariable("h"+(j + 1), ((Entry) tmpList[i].get(j)).getData());
						tw.addVariable("z"+(j + 24),
               						((Entry) tmpList[i].get(j + 23)).getValue());
						tw.addVariable("h"+(j + 24),
               						((Entry) tmpList[i].get(j + 23)).getData());
					}

					tw.addVariable("z23", ((Entry) tmpList[i].get(22)).getValue());
					tw.addVariable("h23", ((Entry) tmpList[i].get(22)).getData());

					tw.write(lottoPath + htmlFiles[i]);

				} else {

					tw.addFile(templatePath + templates[i + 4]);

					for (int j = 0; j < 22; j++) {
						tw.addVariable("z"+(j + 1), j + 1);
						tw.addVariable("h"+(j + 1), haeuf[i][j]);
						tw.addVariable("z"+(j + 24), j + 24);
						tw.addVariable("h"+(j + 24), haeuf[i][j + 23]);
					}
					tw.addVariable("z23", 23);
					tw.addVariable("h23", haeuf[i][22]);
					tw.write(lottoPath + htmlFiles[i + 4]);
				}
			}
			
		} catch (Exception fe) {
			System.err.println(fe);
		}
	}
}