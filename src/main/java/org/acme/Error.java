package org.acme;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;

@TypeDefs({
	@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@RegisterForReflection
@Entity
public class Error extends PanacheEntity {

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	public Configuration configuration;

}
