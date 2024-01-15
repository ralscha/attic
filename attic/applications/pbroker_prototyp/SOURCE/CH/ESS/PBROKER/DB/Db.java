package ch.ess.pbroker.db;

public class Db {

	private Db() { }

	private static KundenTable kundenTable;
	private static AnfragenTable anfragenTable;
	private static AnfragekandidatenTable anfragekandidatenTable;
	private static AnfragelieferantenTable anfragelieferantenTable;
	private static AnfrageskillsTable anfrageskillsTable;
	private static BenutzerTable benutzerTable;
	private static BenutzerrechteTable benutzerrechteTable;
	private static KandidatenTable kandidatenTable;
	private static KandidatenskillsTable kandidatenskillsTable;
	private static LieferantenTable lieferantenTable;
	private static OffertenTable offertenTable;
	private static RechteTable rechteTable;
	private static SkilllevelsTable skilllevelsTable;
	private static SkillsTable skillsTable;
	private static TopicsTable topicsTable;
	private static SkilllevelgroupsTable skilllevelgroupsTable;
	private static VertragTable vertragTable;

	public static KundenTable getKundenTable() {
		if (kundenTable == null)
			kundenTable = new KundenTable();

		return kundenTable;
	}

	public static AnfragenTable getAnfragenTable() {
		if (anfragenTable == null)
			anfragenTable = new AnfragenTable();

		return anfragenTable;
	}

	public static AnfragekandidatenTable getAnfragekandidatenTable() {
		if (anfragekandidatenTable == null)
			anfragekandidatenTable = new AnfragekandidatenTable();

		return anfragekandidatenTable;
	}

	public static AnfragelieferantenTable getAnfragelieferantenTable() {
		if (anfragelieferantenTable == null)
			anfragelieferantenTable = new AnfragelieferantenTable();

		return anfragelieferantenTable;
	}

	public static AnfrageskillsTable getAnfrageskillsTable() {
		if (anfrageskillsTable == null)
			anfrageskillsTable = new AnfrageskillsTable();

		return anfrageskillsTable;
	}

	public static BenutzerTable getBenutzerTable() {
		if (benutzerTable == null)
			benutzerTable = new BenutzerTable();

		return benutzerTable;
	}

	public static BenutzerrechteTable getBenutzerrechteTable() {
		if (benutzerrechteTable == null)
			benutzerrechteTable = new BenutzerrechteTable();

		return benutzerrechteTable;
	}

	public static KandidatenTable getKandidatenTable() {
		if (kandidatenTable == null)
			kandidatenTable = new KandidatenTable();

		return kandidatenTable;
	}

	public static KandidatenskillsTable getKandidatenskillsTable() {
		if (kandidatenskillsTable == null)
			kandidatenskillsTable = new KandidatenskillsTable();

		return kandidatenskillsTable;
	}

	public static LieferantenTable getLieferantenTable() {
		if (lieferantenTable == null)
			lieferantenTable = new LieferantenTable();

		return lieferantenTable;
	}

	public static OffertenTable getOffertenTable() {
		if (offertenTable == null)
			offertenTable = new OffertenTable();

		return offertenTable;
	}

	public static RechteTable getRechteTable() {
		if (rechteTable == null)
			rechteTable = new RechteTable();

		return rechteTable;
	}

	public static SkilllevelsTable getSkilllevelsTable() {
		if (skilllevelsTable == null)
			skilllevelsTable = new SkilllevelsTable();

		return skilllevelsTable;
	}

	public static SkillsTable getSkillsTable() {
		if (skillsTable == null)
			skillsTable = new SkillsTable();

		return skillsTable;
	}

	public static TopicsTable getTopicsTable() {
		if (topicsTable == null)
			topicsTable = new TopicsTable();

		return topicsTable;
	}

	public static SkilllevelgroupsTable getSkilllevelgroupsTable() {
		if (skilllevelgroupsTable == null)
			skilllevelgroupsTable = new SkilllevelgroupsTable();

		return skilllevelgroupsTable;
	}

	public static VertragTable getVertragTable() {
		if (vertragTable == null)
			vertragTable = new VertragTable();

		return vertragTable;
	}

}
