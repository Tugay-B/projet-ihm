/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Planning implements Serializable {

    private Map<Plage, List<Salle>> planning;

    public Planning() {
        this.planning = new HashMap<Plage, List<Salle>>();
    }

    protected Map<Plage, List<Salle>> getPlanning() {
        return this.planning;
    }

    public void ajouterPlage(Plage p) throws Exception {
        if (this.planning.containsKey(p)) {
            throw new Exception("Cette plage existe déjà !");
        } else {
            this.planning.put(p, new ArrayList<Salle>());
        }
    }

    public void ajouterSalle(Plage p, Salle s) throws Exception {
        if (!this.planning.containsKey(p)) {
            throw new Exception("Cette plage n'existe pas !");
        } else if(this.planning.get(p).contains(s)) {
            throw new Exception("Cette salle est déjà réservée !");
        } else {
            for (Salle s1 : this.planning.get(p)) {
                if (s1.getProf().equals(s.getProf())) {
                    throw new Exception("Ce prof a déjà un cours de prévu !");
                }
            }
            List<Salle> l = this.planning.get(p);
            l.add(s);
            this.planning.put(p, l);
        }
    }

    public List<Salle> recupererSalles(Plage p) throws Exception {
        if (this.planning.containsKey(p)) {
            throw new Exception("Cette plage existe déjà !");
        }
        return this.planning.get(p);
    }

    @Override
    public String toString() {
        return "Planning{" + "planning=" + planning + '}';
    }

    public void enregistrer(String nomFichier) throws IOException {
        OutputStream buffer;
        ObjectOutput output;
        try (OutputStream file = new FileOutputStream(nomFichier)) {
            buffer = new BufferedOutputStream(file);
            output = new ObjectOutputStream(buffer);
            output.writeObject(this);
            buffer.close();
            output.close();
        }
    }

    public void ouvrir(String nomFichier) throws FileNotFoundException, IOException, ClassNotFoundException {
        InputStream file = new FileInputStream(nomFichier);
        InputStream buffer = new BufferedInputStream(file);
        ObjectInput input = new ObjectInputStream(buffer);
        Planning p = (Planning) input.readObject();
        this.planning = p.getPlanning();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.planning);
        return hash;
    }

    @Override
    public boolean equals(Object obj
    ) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Planning other = (Planning) obj;
        if (!Objects.equals(this.planning, other.planning)) {
            return false;
        }
        return true;
    }

}
