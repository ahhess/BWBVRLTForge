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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import rltforge.model.Verein;

@Entity
public class Person implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   @Column
   private String vorname;

   @Column
   private String nachname;

   @Column
   private String strasse;

   @Column
   private String plzOrt;

   @Column
   private String email;

   @Column
   private String telefon;

   @Column
   private String mobil;

   @Column
   private String passwort;

   private @OneToMany(mappedBy = "turnierbeauftragter", cascade = CascadeType.ALL)
   Set<Turnier> turniere = new HashSet<Turnier>();

   @Column
   private String passnr;

   @Column
   private String geschlecht;

   private @Temporal(TemporalType.DATE)
   Date geburtstag;

   @ManyToOne
   private Verein verein;

   @ManyToOne
   private Verein ansprechpartnerVerein;

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
         return id.equals(((Person) that).id);
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

   public String getVorname()
   {
      return this.vorname;
   }

   public void setVorname(final String vorname)
   {
      this.vorname = vorname;
   }

   public String getNachname()
   {
      return this.nachname;
   }

   public void setNachname(final String nachname)
   {
      this.nachname = nachname;
   }

   public String getStrasse()
   {
      return this.strasse;
   }

   public void setStrasse(final String strasse)
   {
      this.strasse = strasse;
   }

   public String getPlzOrt()
   {
      return this.plzOrt;
   }

   public void setPlzOrt(final String plzOrt)
   {
      this.plzOrt = plzOrt;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getTelefon()
   {
      return this.telefon;
   }

   public void setTelefon(final String telefon)
   {
      this.telefon = telefon;
   }

   public String getMobil()
   {
      return this.mobil;
   }

   public void setMobil(final String mobil)
   {
      this.mobil = mobil;
   }

   public String getPasswort()
   {
      return this.passwort;
   }

   public void setPasswort(final String passwort)
   {
      this.passwort = passwort;
   }

   public Set<Turnier> getTurniere()
   {
      return this.turniere;
   }

   public void setTurniere(final Set<Turnier> turniere)
   {
      this.turniere = turniere;
   }

   public String getPassnr()
   {
      return this.passnr;
   }

   public void setPassnr(final String passnr)
   {
      this.passnr = passnr;
   }

   public String getGeschlecht()
   {
      return this.geschlecht;
   }

   public void setGeschlecht(final String geschlecht)
   {
      this.geschlecht = geschlecht;
   }

   public Date getGeburtstag()
   {
      return this.geburtstag;
   }

   public void setGeburtstag(final Date geburtstag)
   {
      this.geburtstag = geburtstag;
   }

   public String toString()
   {
      String result = "";
      if (vorname != null && !vorname.trim().isEmpty())
         result += vorname;
      if (nachname != null && !nachname.trim().isEmpty())
         result += " " + nachname;
      if (strasse != null && !strasse.trim().isEmpty())
         result += " " + strasse;
      if (plzOrt != null && !plzOrt.trim().isEmpty())
         result += " " + plzOrt;
      if (email != null && !email.trim().isEmpty())
         result += " " + email;
      if (telefon != null && !telefon.trim().isEmpty())
         result += " " + telefon;
      if (mobil != null && !mobil.trim().isEmpty())
         result += " " + mobil;
      if (passwort != null && !passwort.trim().isEmpty())
         result += " " + passwort;
      if (passnr != null && !passnr.trim().isEmpty())
         result += " " + passnr;
      if (geschlecht != null && !geschlecht.trim().isEmpty())
         result += " " + geschlecht;
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

   public Verein getAnsprechpartnerVerein()
   {
      return this.ansprechpartnerVerein;
   }

   public void setAnsprechpartnerVerein(final Verein ansprechpartnerVerein)
   {
      this.ansprechpartnerVerein = ansprechpartnerVerein;
   }
}