package rltforge.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Set;
import java.util.HashSet;
import rltforge.model.Spielermeldung;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import rltforge.model.Turnier;
import javax.persistence.ManyToOne;
import rltforge.model.Verein;

@Entity
public class Vereinsmeldung implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;

   private @Column(name = "version")
   int version = 0;

   private @OneToMany(mappedBy = "vereinsmeldung", cascade = CascadeType.ALL)
   Set<Spielermeldung> spieler = new HashSet<Spielermeldung>();

   @ManyToOne
   private Turnier turnier;

   @Column
   private String bemerkung;

   @ManyToOne
   private Verein verein;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Vereinsmeldung) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public Set<Spielermeldung> getSpieler()
   {
      return this.spieler;
   }

   public void setSpieler(final Set<Spielermeldung> spieler)
   {
      this.spieler = spieler;
   }

   public Turnier getTurnier()
   {
      return this.turnier;
   }

   public void setTurnier(final Turnier turnier)
   {
      this.turnier = turnier;
   }

   public String getBemerkung()
   {
      return this.bemerkung;
   }

   public void setBemerkung(final String bemerkung)
   {
      this.bemerkung = bemerkung;
   }

   public String toString()
   {
      String result = "";
//      if (bemerkung != null && !bemerkung.trim().isEmpty())
//    	  result += bemerkung;
      if (verein != null && verein.getName() != null && !verein.getName().trim().isEmpty())
    	  result += verein.getName() + " ";
      if (turnier != null && turnier.getName() != null && !turnier.getName().trim().isEmpty())
         result += turnier.getName() + " ";
      return result;
   }

   public Verein getVerein()
   {
      return this.verein;
   }

   public void setVerein(final Verein verein)
   {
      this.verein = verein;
   }
}