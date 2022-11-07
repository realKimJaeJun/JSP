package bean;

public class RegBean {

	private String regstdno;
	private String reglecno;
	private int regmidscore;
	private int regfinalscore;
	private int regtotalscore;
	private String reggrade;
	
	// 추가
	private String lecname;
	private String stdname;
	
	public String getLecname() {
		return lecname;
	}
	public void setLecname(String lecname) {
		this.lecname = lecname;
	}
	public String getStdname() {
		return stdname;
	}
	public void setStdname(String stdname) {
		this.stdname = stdname;
	}
	
	
	public String getRegstdno() {
		return regstdno;
	}
	public void setRegstdno(String regstdno) {
		this.regstdno = regstdno;
	}
	public String getReglecno() {
		return reglecno;
	}
	public void setReglecno(String reglecno) {
		this.reglecno = reglecno;
	}
	public int getRegmidscore() {
		return regmidscore;
	}
	public void setRegmidscore(int regmidscore) {
		this.regmidscore = regmidscore;
	}
	public int getRegfinalscore() {
		return regfinalscore;
	}
	public void setRegfinalscore(int regfinalscore) {
		this.regfinalscore = regfinalscore;
	}
	public int getRegtotalscore() {
		return regtotalscore;
	}
	public void setRegtotalscore(int regtotalscore) {
		this.regtotalscore = regtotalscore;
	}
	public String getReggrade() {
		return reggrade;
	}
	public void setReggrade(String reggrade) {
		this.reggrade = reggrade;
	}
	
	
}
