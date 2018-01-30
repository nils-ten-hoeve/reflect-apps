package nth.meyn.controls.configurator;

import java.util.Arrays;
import java.util.List;

import nth.introspect.ui.style.ContentColor;
import nth.introspect.ui.style.MaterialColorPalette;
import nth.introspect.ui.style.basic.Color;
import nth.meyn.controls.configurator.dom.component.ComponentTemplateService;
import nth.meyn.controls.configurator.dom.equipment.EquipmentTemplate;
import nth.meyn.controls.configurator.dom.equipment.EquipmentTemplateService;
import nth.meyn.controls.configurator.dom.project.ProjectService;
import nth.meyn.controls.configurator.dom.project.ProjectTemplateService;
import nth.meyn.controls.configurator.dom.settings.SettingsRepository;
import nth.meyn.controls.configurator.dom.site.SiteService;
import nth.reflect.javafx.ReflectApplicationForJavaFX;

public class MeynControlsConfigurator extends ReflectApplicationForJavaFX {

	@Override
	public List<Class<?>> getServiceClasses() {
		return Arrays.asList(ComponentTemplateService.class, EquipmentTemplateService.class, ProjectTemplateService.class, SiteService.class, ProjectService.class);
	}

	@Override
	public List<Class<?>> getInfrastructureClasses() {
		return Arrays.asList(SettingsRepository.class);
	}

	@Override
	public Color getPrimaryColor() {
		return MaterialColorPalette.TEAL;
	}

	@Override
	public Color getAccentColor() {
		return MaterialColorPalette.ORANGE;
	}

	@Override
	public ContentColor getContentColor() {
		return ContentColor.WHITE;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
