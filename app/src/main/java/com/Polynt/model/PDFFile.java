package com.Polynt.model;

/**
 * Created by Ryang on 2/11/2016.
 */
public class PDFFile {
    String category;
    String description;
    String name;
    String pdf;
    String series;
    String tradename;
    String modify_date;
    String pdf_url;
    public void setCategory(String category){this.category = category;};
    public String getCategory(){return this.category;};
    public void setDescription(String description){this.description = description;};
    public String getDescription(){return this.description;}
    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}
    public void setPdf(String pdf){this.pdf = pdf;}
    public String getPdf(){return this.pdf;}
    public void setSeries(String series){this.series = series;}
    public String getSeries(){return this.series;}
    public void setTradename(String tradename){this.tradename = tradename;}
    public String getTradename(){return this.tradename;}
    public void setModify_date(String modify_date){this.modify_date = modify_date;}
    public String getModify_date(){return this.modify_date;}
    public void setPdf_url(String pdf_url){this.pdf_url = pdf_url;}
    public String getPdf_url(){return this.pdf_url;}

    public boolean isEqual(PDFFile otherFile) {
        if (category.equalsIgnoreCase(otherFile.category) == false) return false;
        if (description.equalsIgnoreCase(otherFile.description) == false) return false;
        if (name.equalsIgnoreCase(otherFile.name) == false) return false;
        if (pdf.equalsIgnoreCase(otherFile.pdf) == false) return false;
        if (series.equalsIgnoreCase(otherFile.series) == false) return false;
        if (tradename.equalsIgnoreCase(otherFile.tradename) == false) return false;
        if (modify_date.equalsIgnoreCase(otherFile.modify_date) == false) return false;
        if (pdf_url.equalsIgnoreCase(otherFile.pdf_url) == false) return false;

        return true;
    }

}
