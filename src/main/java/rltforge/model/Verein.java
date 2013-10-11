package rltforge.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import rltforge.model.Vereinsmeldung;

@Entity
public class Verein implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   private @OneToMany(mappedBy = "verein", cascade = CascadeType.ALL)
   Set<Person> spieler = new HashSet<Person>();

   @Column
   private String davor;

   @Column
   private String name;

   @Column
   private String kurz;

   @Column
   private String region;

   @Column
   private String bemerkung;

   private @OneToMany(mappedBy = "ansprechpartnerVerein")
   Set<Person> ansprechpartner = new HashSet<Person>();

   private @OneToMany(mappedBy = "verein", cascade = CascadeType.ALL)
   Set<Vereinsmeldung> meldungen = new HashSet<Vereinsmeldung>();

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
         return id.equals(((Verein) that).id);
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

   public Set<Person> getSpieler()
   {
      return this.spieler;
   }

   public void setSpieler(final Set<Person> spieler)
   {
      this.spieler = spieler;
   }

   public String getDavor()
   {
      return this.davor;
   }

   public void setDavor(final String davor)
   {
      this.davor = davor;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getKurz()
   {
      return this.kurz;
   }

   public void setKurz(final String kurz)
   {
      this.kurz = kurz;
   }

   public String getRegion()
   {
      return this.region;
   }

   public void setRegion(final String region)
   {
      this.region = region;
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
      if (davor != null && !davor.trim().isEmpty())
         result += davor;
      if (name != null && !name.trim().isEmpty())
         result += " " + name;
      if (kurz != null && !kurz.trim().isEmpty())
         result += " " + kurz;
      if (region != null && !region.trim().isEmpty())
         result += " " + region;
      if (bemerkung != null && !bemerkung.trim().isEmpty())
         result += " " + bemerkung;
      return result;
   }

   public Set<Person> getAnsprechpartner()
   {
      return this.ansprechpartner;
   }

   public void setAnsprechpartner(final Set<Person> ansprechpartner)
   {
      this.ansprechpartner = ansprechpartner;
   }

   public Set<Vereinsmeldung> getMeldungen()
   {
      return this.meldungen;
   }

   public void setMeldungen(final Set<Vereinsmeldung> meldungen)
   {
      this.meldungen = meldungen;
   }
}