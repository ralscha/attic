
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrapaItem implements Comparable {

	private int year;
	private int month;
	private String cif;
	private String branch;
	private String kst;
	private String legalEntity;
	private int crapaCode;
	private BigDecimal amount;

	public static CrapaItem createFromResultSet(ResultSet rs) throws SQLException {
		return new CrapaItem(rs.getInt(1), rs.getInt(2), rs.getString(3),
								rs.getString(4), rs.getString(5), rs.getString(6),
								rs.getInt(7), new BigDecimal(rs.getDouble(8)));
	}

	public CrapaItem() {
		this(0, 0, null, null, null, null, 0, null);
	}

	public CrapaItem(int year, int month, String cif, String branch, String kst, String le, 
						 int cc, BigDecimal amount) {
       this.year = year;
       this.month = month;
		this.cif = cif;
		this.legalEntity = le;
		this.branch = branch;
		this.kst = kst;
		this.crapaCode = cc;
		this.amount = amount;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getMonth() {
		return month;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCif() {
		return cif;
	}

	public void setLegalEntity(String le) {
		this.legalEntity = le;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getBranch() {
		return branch;
	}

	public void setKst(String kst) {
		this.kst = kst;
	}

	public String getKst() {
		return kst;
	}

	public void setCrapaCode(int cc) {
		this.crapaCode = cc;
	}

	public int getCrapaCode() {
		return crapaCode;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public boolean equals(CrapaItem ci) {
		return ((year == ci.getYear()) && (month == ci.getMonth()) && (branch.equals(ci.getBranch())) && (kst.equals(ci.getKst())) &&
        		(legalEntity.equals(ci.getLegalEntity())) && (crapaCode == ci.getCrapaCode()));
	}

	public int compareTo(Object o) {
		int r;
		CrapaItem other = (CrapaItem)o;

		r = getBranch().compareTo(other.getBranch());
		if (r == 0) {
			r = getKst().compareTo(other.getKst());
			if (r == 0) {
				r = getLegalEntity().compareTo(other.getLegalEntity());
				if (r == 0) {
					r = getCrapaCode() - other.getCrapaCode();
				}
			}
		}
		return r;
	}

}