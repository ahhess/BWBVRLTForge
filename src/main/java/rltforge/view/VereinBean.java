package rltforge.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import rltforge.model.Verein;

/**
 * Backing bean for Verein entities.
 * <p>
 * This class provides CRUD functionality for all Verein entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class VereinBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Verein entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private Verein verein;

   public Verein getVerein()
   {
      return this.verein;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
      }

      if (this.id == null)
      {
         this.verein = this.example;
      }
      else
      {
         this.verein = findById(getId());
      }
   }

   public Verein findById(Long id)
   {

      return this.entityManager.find(Verein.class, id);
   }

   /*
    * Support updating and deleting Verein entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.verein);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.verein);
            return "view?faces-redirect=true&id=" + this.verein.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         this.entityManager.remove(findById(getId()));
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching Verein entities with pagination
    */

   private int page;
   private long count;
   private List<Verein> pageItems;

   private Verein example = new Verein();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public Verein getExample()
   {
      return this.example;
   }

   public void setExample(Verein example)
   {
      this.example = example;
   }

   public void search()
   {
      this.page = 0;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<Verein> root = countCriteria.from(Verein.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Verein> criteria = builder.createQuery(Verein.class);
      root = criteria.from(Verein.class);
      TypedQuery<Verein> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Verein> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String davor = this.example.getDavor();
      if (davor != null && !"".equals(davor))
      {
         predicatesList.add(builder.like(root.<String> get("davor"), '%' + davor + '%'));
      }
      String name = this.example.getName();
      if (name != null && !"".equals(name))
      {
         predicatesList.add(builder.like(root.<String> get("name"), '%' + name + '%'));
      }
      String kurz = this.example.getKurz();
      if (kurz != null && !"".equals(kurz))
      {
         predicatesList.add(builder.like(root.<String> get("kurz"), '%' + kurz + '%'));
      }
      String region = this.example.getRegion();
      if (region != null && !"".equals(region))
      {
         predicatesList.add(builder.like(root.<String> get("region"), '%' + region + '%'));
      }
      String bemerkung = this.example.getBemerkung();
      if (bemerkung != null && !"".equals(bemerkung))
      {
         predicatesList.add(builder.like(root.<String> get("bemerkung"), '%' + bemerkung + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Verein> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Verein entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Verein> getAll()
   {

      CriteriaQuery<Verein> criteria = this.entityManager.getCriteriaBuilder().createQuery(Verein.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(Verein.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final VereinBean ejbProxy = this.sessionContext.getBusinessObject(VereinBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context, UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context, UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((Verein) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Verein add = new Verein();

   public Verein getAdd()
   {
      return this.add;
   }

   public Verein getAdded()
   {
      Verein added = this.add;
      this.add = new Verein();
      return added;
   }
}