package nth.meyn.controls.configurator.dom.equipment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import nth.introspect.layer5provider.reflection.behavior.description.Description;
import nth.meyn.controls.configurator.dom.component.ComponentTemplateTag;
import nth.meyn.controls.configurator.dom.function.FunctionTemplate;
import nth.meyn.controls.configurator.dom.named.Named;

@Description(englishDescription = "A template for a machine or software product (e.g. a shackle washer, plucker, re-hanger or rapid) made out of components")
public class EquipmentTemplate implements Named {
	private String name;
	private String abbreviation;
	private LocalDateTime version;
	private List<ComponentTemplateTag> tags;
	private List<FunctionTemplate> functionTemplates;
	private List<EquipmentOption> options;

	public EquipmentTemplate() {
	  tags=new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public LocalDateTime getVersion() {
		return version;
	}
	public void setVersion(LocalDateTime version) {
		this.version = version;
	}
	public List<ComponentTemplateTag> getTags() {
		return tags;
	}
	public void setTags(List<ComponentTemplateTag> tags) {
		this.tags = tags;
	}

	public List<FunctionTemplate> getFunctionTemplates() {
		return functionTemplates;
	}

	public void setFunctionTemplates(List<FunctionTemplate> functionTemplates) {
		this.functionTemplates = functionTemplates;
	}

	public List<EquipmentOption> getOptions() {
		return options;
	}

	public void setOptions(List<EquipmentOption> options) {
		this.options = options;
	}


		
	
	// TODO add attributes
	// TODO add electric schematic template (EPlan macro's)
	// TODO add plc program template
	// TODO add display program template
	// TODO add FAT template doc
	// TODO add IO

	
	

}