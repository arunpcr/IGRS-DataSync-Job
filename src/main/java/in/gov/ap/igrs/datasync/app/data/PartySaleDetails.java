package in.gov.ap.igrs.datasync.app.data;

import java.util.Arrays;

public class PartySaleDetails {
	int documentId;
	int saleId;
	int executant_party_ids[];
	int claimant_party_ids[];
	int presnt_party_ids[];
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public int getSaleId() {
		return saleId;
	}
	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}
	public int[] getExecutant_party_ids() {
		return executant_party_ids;
	}
	public void setExecutant_party_ids(int[] executant_party_ids) {
		this.executant_party_ids = executant_party_ids;
	}
	public int[] getClaimant_party_ids() {
		return claimant_party_ids;
	}
	public void setClaimant_party_ids(int[] claimant_party_ids) {
		this.claimant_party_ids = claimant_party_ids;
	}
	public int[] getPresnt_party_ids() {
		return presnt_party_ids;
	}
	public void setPresnt_party_ids(int[] presnt_party_ids) {
		this.presnt_party_ids = presnt_party_ids;
	}
	@Override
	public String toString() {
		return "PartySaleDetails [documentId=" + documentId + ", saleId=" + saleId + ", executant_party_ids="
				+ Arrays.toString(executant_party_ids) + ", claimant_party_ids=" + Arrays.toString(claimant_party_ids)
				+ ", presnt_party_ids=" + Arrays.toString(presnt_party_ids) + "]";
	}
	

}
