/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Tuan Anh(Gia Truong)
 */
public class ModuleDTO {

    private int moduleid;
    private String modulename;
    private int courseid;
    private int module_number;

    public ModuleDTO() {
    }

    public ModuleDTO(int moduleid, String modulename) {
        this.moduleid = moduleid;
        this.modulename = modulename;
    }
    public ModuleDTO(int moduleid, String modulename, int module_number) {
        this.moduleid = moduleid;
        this.modulename = modulename;
        this.module_number = module_number;
    }

    public ModuleDTO(String modulename, int module_number) {
        this.modulename = modulename;
        this.module_number = module_number;
    }
    
    

//    public ModuleDTO(int moduleid, String modulename, int courseid) {
//        this.moduleid = moduleid;
//        this.modulename = modulename;
//        this.courseid = courseid;
//    }

    public int getModuleid() {
        return moduleid;
    }

    public void setModuleid(int moduleid) {
        this.moduleid = moduleid;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public int getCourseid() {
        return courseid;
    }

    public void setCourseid(int courseid) {
        this.courseid = courseid;
    }

    public int getModule_number() {
        return module_number;
    }

    public void setModule_number(int module_number) {
        this.module_number = module_number;
    }

    @Override
    public String toString() {
        return "Module{" + "moduleid=" + moduleid + ", modulename=" + modulename + ", courseid=" + courseid + '}';
    }

}
