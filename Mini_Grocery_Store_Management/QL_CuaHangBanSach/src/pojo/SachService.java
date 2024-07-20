/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;
import dao.SachDAO;
/**
 *
 * @author MSII
 */
public class SachService {
    private SachDAO sachDAO;

    public SachService() {
        this.sachDAO = new SachDAO();
    }

    public boolean deleteSach(String maSach) {
        return sachDAO.deleteSach(maSach);
    }
}
