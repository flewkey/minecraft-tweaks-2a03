package party._2a03.mc.server;

import org.json.JSONArray;
import net.minecraft.world.dimension.DimensionType;
import party._2a03.mc.server.Config;

public class PlayerPosition {
	public double x;
	public double y;
	public double z;
	public float yaw;
	public float pitch;
	public DimensionType dimensiontype;

	public PlayerPosition() {
	}

	public PlayerPosition(JSONArray data) {
		int dimension_id = data.getInt(5);
		if (dimension_id != -2) {
			this.x = data.getDouble(0);
			this.y = data.getDouble(1);
			this.z = data.getDouble(2);
			this.yaw = data.getNumber(3).floatValue();
			this.pitch = data.getNumber(4).floatValue();
			this.dimensiontype = DimensionType.byRawId(dimension_id);
		}
	}

	public PlayerPosition(double p_x, double p_y, double p_z, float p_yaw, float p_pitch, DimensionType p_dimensiontype) {
		this.x = p_x;
		this.y = p_y;
		this.z = p_z;
		this.yaw = p_yaw;
		this.pitch = p_pitch;
		this.dimensiontype = p_dimensiontype;
	}

	public JSONArray getJSON() {
		JSONArray json = new JSONArray();
		if (this.dimensiontype != null) {
			json.put(this.x);
			json.put(this.y);
			json.put(this.z);
			json.put(this.yaw);
			json.put(this.pitch);
			json.put(this.dimensiontype.getRawId());
		} else {
			json.put(0);
			json.put(0);
			json.put(0);
			json.put(0);
			json.put(0);
			json.put(-2);
		}
		return json;
	}
}