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

import rltforge.model.Vereinsmeldung;
import rltforge.model.Turnier;
import rltforge.model.Verein;

/**
 * Backing bean for Vereinsmeldung entities.
 * <p>
 * This class provides CRUD functionality for all Vereinsmeldung entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class VereinsmeldungBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Vereinsmeldung entities
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

   private Vereinsmeldung vereinsmeldung;

   public Vereinsmeldung getVereinsmeldung()
   {
      return this.vereinsmeldung;
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
         this.vereinsmeldung = this.example;
      }
      else
      {
         this.vereinsmeldung = findById(getId());
      }
   }

   public Vereinsmeldung findById(Long id)
   {

      return this.entityManager.find(Vereinsmeldung.class, id);
   }

   /*
    * Support updating and deleting Vereinsmeldung entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.vereinsmeldung);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.vereinsmeldung);
            return "view?faces-redirect=true&id=" + this.vereinsmeldung.getId();
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
    * Support searching Vereinsmeldung entities with pagination
    */

   private int page;
   private long count;
   private List<Vereinsmeldung> pageItems;

   private Vereinsmeldung example = new Vereinsmeldung();

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

   public Vereinsmeldung getExample()
   {
      return this.example;
   }

   public void setExample(Vereinsmeldung example)
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
      Root<Vereinsmeldung> root = countCriteria.from(Vereinsmeldung.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Vereinsmeldung> criteria = builder.createQuery(Vereinsmeldung.class);
      root = criteria.from(Vereinsmeldung.class);
      TypedQuery<Vereinsmeldung> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Vereinsmeldung> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      int version = this.example.getVersion();
      if (version != 0)
      {
         predicatesList.add(builder.equal(root.get("version"), version));
      }
      Turnier turnier = this.example.getTurnier();
      if (turnier != null)
      {
         predicatesList.add(builder.equal(root.get("turnier"), turnier));
      }
      String bemerkung = this.example.getBemerkung();
      if (bemerkung != null && !"".equals(bemerkung))
      {
         predicatesList.add(builder.like(root.<String> get("bemerkung"), '%' + bemerkung + '%'));
      }
      Verein verein = this.example.getVerein();
      if (verein != null)
      {
         predicatesList.add(builder.equal(root.get("verein"), verein));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Vereinsmeldung> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Vereinsmeldung entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Vereinsmeldung> getAll()
   {

      CriteriaQuery<Vereinsmeldung> criteria = this.entityManager.getCriteriaBuilder().createQuery(Vereinsmeldung.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(Vereinsmeldung.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final VereinsmeldungBean ejbProxy = this.sessionContext.getBusinessObject(VereinsmeldungBean.class);

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

            return String.valueOf(((Vereinsmeldung) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Vereinsmeldung add = new Vereinsmeldung();

   public Vereinsmeldung getAdd()
   {
      return this.add;
   }

   public Vereinsmeldung getAdded()
   {
      Vereinsmeldung added = this.add;
      this.add = new Vereinsmeldung();
      return added;
   }
}