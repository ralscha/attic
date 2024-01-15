import javax.swing.*;
import javax.swing.table.*;
import java.text.*;
import java.io.*;
import COM.odi.*;
import COM.odi.util.*;

public class FLTableModel extends LiabilityTableModel {

	public FLTableModel() {
		super();
		number_of_rows = 0;
		number_of_columns = 13;

		names = new String[number_of_columns];
		alignments = new int[number_of_columns];
		width = new int[number_of_columns];
		classes = new Class[number_of_columns];

		names[0] = "Sequence Number";
		names[1] = "Dossier Number";
		names[2] = "Currency";
		names[3] = "Amount";
		names[4] = "Debit Account Number";
		names[5] = "Credit Account Number";
		names[6] = "Value Date";
		names[7] = "Exchange Rate";
		names[8] = "Processing Center";
		names[9] = "Customer Reference";
		names[10] = "GTF Type";
		names[11] = "Dossier Item";
		names[12] = "BU Code";

		alignments[0] = SwingConstants.RIGHT;
		alignments[1] = SwingConstants.RIGHT;
		alignments[2] = SwingConstants.LEFT;
		alignments[3] = SwingConstants.RIGHT;
		alignments[4] = SwingConstants.RIGHT;
		alignments[5] = SwingConstants.RIGHT;
		alignments[6] = SwingConstants.RIGHT;
		alignments[7] = SwingConstants.RIGHT;
		alignments[8] = SwingConstants.RIGHT;
		alignments[9] = SwingConstants.LEFT;
		alignments[10] = SwingConstants.LEFT;
		alignments[11] = SwingConstants.LEFT;
		alignments[12] = SwingConstants.LEFT;

		width[0] = 20;
		width[1] = 40;
		width[2] = 30;
		width[3] = 100;
		width[4] = 135;
		width[5] = 135;
		width[6] = 80;
		width[7] = 80;
		width[8] = 32;
		width[9] = 160;
		width[10] = 130;
		width[11] = 130;
		width[12] = 40;

		classes[0] = Integer.class;
		classes[1] = Integer.class;
		classes[2] = String.class;
		classes[3] = String.class;
		classes[4] = String.class;
		classes[5] = String.class;
		classes[6] = String.class;
		classes[7] = String.class;
		classes[8] = String.class;
		classes[9] = String.class;
		classes[10] = String.class;
		classes[11] = String.class;
		classes[12] = String.class;

		Transaction tr = DbManager.startReadTransaction();
		updateData();
		DbManager.commitTransaction(tr);

	}

	Collection getCollection() {
		return DbManager.getFirmCollection();
	}

	int getCollectionSize() {
		return DbManager.getFirmCollectionSize();
	}

	void fillDataArray(Iterator e) {
		int i = 0;
		FirmLiability fl;
		while (e.hasNext()) {
			fl = (FirmLiability) e.next();
			dataArray[i][0] = new Integer(fl.getSequence_number());
			dataArray[i][1] = new Integer(fl.getGtf_number());
			dataArray[i][2] = fl.getCurrency();
			dataArray[i][3] = form.format(fl.getAmount());
			dataArray[i][4] = accountFormat(fl.getDeb_acct_number());
			dataArray[i][5] = accountFormat(fl.getCre_acct_number());
			dataArray[i][6] = dateFormat(fl.getValue_date().toString());
			dataArray[i][7] = form2.format(fl.getExchange_rate());
			dataArray[i][8] = fl.getGtf_proc_center();
			dataArray[i][9] = fl.getCustomer_ref();
			dataArray[i][10] = fl.getGtf_type();
			dataArray[i][11] = fl.getDossier_item();
			dataArray[i][12] = fl.getBu_code();
			i++;
		}
		number_of_rows = i;
	}

}