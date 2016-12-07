package fr.ciag.planning.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CIAGItem {
	private String topSup="N";
	
	private Date createDate=null;
	
	private Date ModifyDate=null;
	
	private Date DeleteDate=null;

	@Column(name="topSup", length=1)
	public String getTopSup() {
		return topSup;
	}

	public void setTopSup(String topSup) {
		this.topSup = topSup;
	}

	@Column(name="createDate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name="modifyDate")
	public Date getModifyDate() {
		return ModifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		ModifyDate = modifyDate;
	}

	@Column(name="deleteDate")
	public Date getDeleteDate() {
		return DeleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		DeleteDate = deleteDate;
	}

}
