package gka.dzfp.bean;

public class SFINFO {
    private String sfxmId;
    private String sfxmVal;

    public SFINFO() {
    }

    public SFINFO(String sfxmId, String sfxmVal) {
        this.sfxmId = sfxmId;
        this.sfxmVal = sfxmVal;
    }

    public String getSfxmId() {
        return sfxmId;
    }

    public void setSfxmId(String sfxmId) {
        this.sfxmId = sfxmId;
    }

    public String getSfxmVal() {
        return sfxmVal;
    }

    public void setSfxmVal(String sfxmVal) {
        this.sfxmVal = sfxmVal;
    }
}
