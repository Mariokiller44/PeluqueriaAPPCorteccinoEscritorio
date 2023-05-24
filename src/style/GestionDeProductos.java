package style;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class GestionDeProductos
	extends FlatIntelliJLaf
{
	public static final String NAME = "GestionDeProductos";

	public static boolean setup() {
		return setup( new GestionDeProductos() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, GestionDeProductos.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
