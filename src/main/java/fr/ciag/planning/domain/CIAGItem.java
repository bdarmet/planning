package fr.ciag.planning.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CIAGItem {

	@Id
	@GeneratedValue
	@Column(name="XUXUID")
	private long id;
	
	@Column(name="topSup", length=1)
	private String topSup="N";
	
	@Column(name="createDate")
	private Date createDate=null;
	
	@Column(name="modifyDate")
	private Date modifyDate=null;
	
	@Column(name="deleteDate")
	private Date deleteDate=null;

	public String getTopSup() {
		return topSup;
	}

	public void setTopSup(String topSup) {
		this.topSup = topSup;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

}
