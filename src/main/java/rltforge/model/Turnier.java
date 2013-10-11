package rltforge.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import rltforge.model.Person;
import javax.persistence.ManyToOne;
import java.util.Set;
import java.util.HashSet;
import rltforge.model.Vereinsmeldung;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
public class Turnier implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   @Column
   private String name;

   @Column
   private String ort;

   private @Temporal(TemporalType.DATE)
   Date datum;

   private @Temporal(TemporalType.DATE)
   Date anmeldenAb;

   private @Temporal(TemporalType.DATE)
   Date anmeldenBis;

   @Column
   private String email;

   @ManyToOne
   private Person turnierbeauftragter;

   private @OneToMany(mappedBy = "turnier", cascade = CascadeType.ALL)
   Set<Vereinsmeldung> vereine = new HashSet<Vereinsmeldung>();

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
         return id.equals(((Turnier) that).id);
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

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getOrt()
   {
      return this.ort;
   }

   public void setOrt(final String ort)
   {
      this.ort = ort;
   }

   public Date getDatum()
   {
      return this.datum;
   }

   public void setDatum(final Date datum)
   {
      this.datum = datum;
   }

   public Date getAnmeldenAb()
   {
      return this.anmeldenAb;
   }

   public void setAnmeldenAb(final Date anmeldenAb)
   {
      this.anmeldenAb = anmeldenAb;
   }

   public Date getAnmeldenBis()
   {
      return this.anmeldenBis;
   }

   public void setAnmeldenBis(final Date anmeldenBis)
   {
      this.anmeldenBis = anmeldenBis;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String toString()
   {
      String result = "";
      if (name != null && !name.trim().isEmpty())
         result += name;
      if (ort != null && !ort.trim().isEmpty())
         result += " " + ort;
      if (email != null && !email.trim().isEmpty())
         result += " " + email;
      return result;
   }

   public Person getTurnierbeauftragter()
   {
      return this.turnierbeauftragter;
   }

   public void setTurnierbeauftragter(final Person turnierbeauftragter)
   {
      this.turnierbeauftragter = turnierbeauftragter;
   }

   public Set<Vereinsmeldung> getVereine()
   {
      return this.vereine;
   }

   public void setVereine(final Set<Vereinsmeldung> vereine)
   {
      this.vereine = vereine;
   }
}