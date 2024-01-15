package gtf.mbooker;

import javax.swing.*;
import javax.swing.table.*;
import java.text.*;
import java.io.*;
import java.util.*;
import java.sql.SQLException;

public class CLTableModel extends LiabilityTableModel {


	public CLTableModel() {
		super();
		number_of_rows = 0;
		number_of_columns = 15;

		names = new String[number_of_columns];
		alignments = new int[number_of_columns];
		width = new int[number_of_columns];
		classes = new Class[number_of_columns];

		names[0] = "Sequence Number";
		names[1] = "Type";
		names[2] = "Dossier Number";
		names[3] = "Currency";
		names[4] = "Amount";
		names[5] = "Account Number";
		names[6] = "Expiry Date";
		names[7] = "Exchange Rate";
		names[8] = "Processing Center";
		names[9] = "Customer Reference";
		names[10] = "GTF Type";
		names[11] = "Dossier Item";
		names[12] = "BU Code";
		names[13] = "Creator";
		names[14] = "Timestamp";
		
		alignments[0] = SwingConstants.RIGHT;
		alignments[1] = SwingConstants.CENTER;
		alignments[2] = SwingConstants.RIGHT;
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
		alignments[13] = SwingConstants.LEFT;
		alignments[14] = SwingConstants.RIGHT;
		
		width[0] = 20;
		width[1] = 10;
		width[2] = 40;
		width[3] = 30;
		width[4] = 100;
		width[5] = 135;
		width[6] = 80;
		width[7] = 80;
		width[8] = 32;
		width[9] = 30;
		width[10] = 130;
		width[11] = 30;
		width[12] = 40;
		width[13] = 70;
		width[14] = 160;
		
		classes[0] = Integer.class;
		classes[1] = String.class;
		classes[2] = Integer.class;
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
		classes[13] = String.class;
		classes[14] = String.class;
		
		updateData();
	}


	Iterator getCollectionIterator() {
		try {
			M_CONTLIABTable mct = new M_CONTLIABTable(gtf.crapa.CrapaDbManager.getConnection());
			return mct.select();
		} catch (SQLException sqle) {
			System.err.println(sqle);
			return null;
		}
	}

	int getCollectionSize() {
		try {
			M_CONTLIABTable mct = new M_CONTLIABTable(gtf.crapa.CrapaDbManager.getConnection());
			return mct.count();
		} catch (SQLException sqle) {
			System.err.println(sqle);
			return 0;
		}
	}

	void fillDataArray(Iterator e) {
		ContingentLiability cl;
		int i = 0;

		while (e.hasNext()) {
			cl = (ContingentLiability) e.next();

			dataArray[i][0] = new Integer(cl.getSequence_number());
			dataArray[i][1] = cl.getType();
			dataArray[i][2] = new Integer(cl.getGtf_number());
			dataArray[i][3] = cl.getCurrency();
			dataArray[i][4] = form.format(cl.getAmount());
			dataArray[i][5] = accountFormat(cl.getAccount_number());
			dataArray[i][6] = dateFormat(cl.getExpiry_date().toString());
			dataArray[i][7] = form2.format(cl.getExchange_rate());
			dataArray[i][8] = cl.getGtf_proc_center();
			dataArray[i][9] = cl.getCustomer_ref();
			dataArray[i][10] = cl.getGtf_type();
			dataArray[i][11] = cl.getDossier_item();
			dataArray[i][12] = cl.getBu_code();
			dataArray[i][13] = cl.getCreator();
			dataArray[i][14] = tsformat.format(cl.getTimestamp());
			i++;
		}
		number_of_rows = i;
	}
}