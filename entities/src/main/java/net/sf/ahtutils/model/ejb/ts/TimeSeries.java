package net.sf.ahtutils.model.ejb.ts;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.ts.UtilsTimeSeries;
import net.sf.ahtutils.model.ejb.status.Description;
import net.sf.ahtutils.model.ejb.status.AhtUtilsLang;
import net.sf.ahtutils.model.qualifier.EjbErNode;

@EjbErNode(name="Time Series",category="ts",subset="ts")
public class TimeSeries implements Serializable,EjbRemoveable,EjbPersistable,
								UtilsTimeSeries<AhtUtilsLang,Description,TsCategory,TsUnit,TimeSeries,TsEntity,TsInterval,TsData>
{
	public static final long serialVersionUID=1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@NotNull @ManyToOne
	private TsCategory category;
	@Override public TsCategory getCategory() {return category;}
	@Override public void setCategory(TsCategory category) {this.category = category;}
	
	@NotNull @ManyToOne
	private TsInterval interval;
	@Override public TsInterval getInterval() {return interval;}
	@Override public void setInterval(TsInterval interval) {this.interval = interval;}
	
	@NotNull @ManyToOne
	private TsEntity entity;
	@Override public TsEntity getEntity() {return entity;}
	@Override public void setEntity(TsEntity entity) {this.entity = entity;}
}