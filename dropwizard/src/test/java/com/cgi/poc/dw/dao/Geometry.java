/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.dao;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author dawna.floyd
 */
public class Geometry {
    Geometry ( ) { }
    
    Geometry (BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
        
    }
    public BigDecimal x;
    public BigDecimal y;
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Geometry)) {
            return false;
        }
        Geometry other = (Geometry) object;
         if ((this.x == null && other.x != null) || 
             (this.y != null && !this.y.equals(other.y))) {
             return false;
         }
             return true;

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.x);
        hash = 89 * hash + Objects.hashCode(this.y);
        return hash;
    }

}
