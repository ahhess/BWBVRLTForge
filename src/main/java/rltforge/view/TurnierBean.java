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

import rltforge.model.Turnier;
import rltforge.model.Person;

/**
 * Backing bean for Turnier entities.
 * <p>
 * This class provides CRUD functionality for all Turnier entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TurnierBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Turnier entities
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

   private Turnier turnier;

   public Turnier getTurnier()
   {
      return this.turnier;
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
         this.turnier = this.example;
      }
      else
      {
         this.turnier = findById(getId());
      }
   }

   public Turnier findById(Long id)
   {

      return this.entityManager.find(Turnier.class, id);
   }

   /*
    * Support updating and deleting Turnier entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.turnier);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.turnier);
            return "view?faces-redirect=true&id=" + this.turnier.getId();
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
    * Support searching Turnier entities with pagination
    */

   private int page;
   private long count;
   private List<Turnier> pageItems;

   private Turnier example = new Turnier();

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

   public Turnier getExample()
   {
      return this.example;
   }

   public void setExample(Turnier example)
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
      Root<Turnier> root = countCriteria.from(Turnier.class);
      countCriteria = countCriteria.select(builder.count(root)).where(getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria).getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Turnier> criteria = builder.createQuery(Turnier.class);
      root = criteria.from(Turnier.class);
      TypedQuery<Turnier> query = this.entityManager.createQuery(criteria.select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Turnier> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String name = this.example.getName();
      if (name != null && !"".equals(name))
      {
         predicatesList.add(builder.like(root.<String> get("name"), '%' + name + '%'));
      }
      String ort = this.example.getOrt();
      if (ort != null && !"".equals(ort))
      {
         predicatesList.add(builder.like(root.<String> get("ort"), '%' + ort + '%'));
      }
      String email = this.example.getEmail();
      if (email != null && !"".equals(email))
      {
         predicatesList.add(builder.like(root.<String> get("email"), '%' + email + '%'));
      }
      Person turnierbeauftragter = this.example.getTurnierbeauftragter();
      if (turnierbeauftragter != null)
      {
         predicatesList.add(builder.equal(root.get("turnierbeauftragter"), turnierbeauftragter));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Turnier> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Turnier entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Turnier> getAll()
   {

      CriteriaQuery<Turnier> criteria = this.entityManager.getCriteriaBuilder().createQuery(Turnier.class);
      return this.entityManager.createQuery(criteria.select(criteria.from(Turnier.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final TurnierBean ejbProxy = this.sessionContext.getBusinessObject(TurnierBean.class);

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

            return String.valueOf(((Turnier) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Turnier add = new Turnier();

   public Turnier getAdd()
   {
      return this.add;
   }

   public Turnier getAdded()
   {
      Turnier added = this.add;
      this.add = new Turnier();
      return added;
   }
}