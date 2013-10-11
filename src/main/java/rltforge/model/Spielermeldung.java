package rltforge.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Spielermeldung implements Serializable
{

   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   @ManyToOne
   private Vereinsmeldung vereinsmeldung;

   @ManyToOne
   private Person spieler;

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

   public String toString()
   {
      String result = "";
      if (id != null)
         result += id;
      return result;
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
         return id.equals(((Spielermeldung) that).id);
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

   public Vereinsmeldung getVereinsmeldung()
   {
      return this.vereinsmeldung;
   }

   public void setVereinsmeldung(final Vereinsmeldung vereinsmeldung)
   {
      this.vereinsmeldung = vereinsmeldung;
   }

   public Person getSpieler()
   {
      return this.spieler;
   }

   public void setSpieler(final Person spieler)
   {
      this.spieler = spieler;
   }

}