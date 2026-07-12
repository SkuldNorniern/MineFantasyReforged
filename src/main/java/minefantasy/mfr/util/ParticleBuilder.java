package minefantasy.mfr.util;

import minefantasy.mfr.MineFantasyReforged;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Random;

/**
 * Singleton builder that constructs and spawns custom particles. Client-side only.
 * Port note: spawn() is stubbed until CustomParticle and the particle engine registration are ported.
 */
@OnlyIn(Dist.CLIENT)
public final class ParticleBuilder {

	public static final ParticleBuilder instance = new ParticleBuilder();

	private boolean building = false;

	private Identifier type;
	private double x, y, z;
	private double vx, vy, vz;
	private float r, g, b;
	private float fr, fg, fb;
	private double radius;
	private double rpt;
	private int lifetime;
	private boolean gravity;
	private boolean shaded;
	private boolean collide;
	private float scale;
	private Entity entity;
	private float yaw, pitch;
	private double tx, ty, tz;
	private double tvx, tvy, tvz;
	private Entity target;
	private long seed;
	private double length;

	public static class Type {
		public static final Identifier SPARK = Identifier.fromNamespaceAndPath(MineFantasyReforged.MOD_ID, "spark");
	}

	private ParticleBuilder() {
		reset();
	}

	public static ParticleBuilder create(Identifier type) {
		return ParticleBuilder.instance.particle(type);
	}

	public ParticleBuilder particle(Identifier type) {
		if (building) throw new IllegalStateException("Already building! Particle being built: " + getCurrentParticleString());
		this.type = type;
		this.building = true;
		return this;
	}

	private String getCurrentParticleString() {
		return String.format("[ Type: %s, Position: (%s, %s, %s), Velocity: (%s, %s, %s), Colour: (%s, %s, %s), "
				+ "Fade: (%s, %s, %s), Radius: %s, Revs/tick: %s, Lifetime: %s, Scale: %s, Entity: %s ]",
				type, x, y, z, vx, vy, vz, r, g, b, fr, fg, fb, radius, rpt, lifetime, scale, entity);
	}

	public ParticleBuilder pos(double x, double y, double z) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public ParticleBuilder pos(Vec3 pos) {
		return pos(pos.x, pos.y, pos.z);
	}

	public ParticleBuilder vel(double vx, double vy, double vz) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		return this;
	}

	public ParticleBuilder vel(Vec3 vel) {
		return vel(vel.x, vel.y, vel.z);
	}

	public ParticleBuilder clr(float r, float g, float b) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.r = Mth.clamp(r, 0, 1);
		this.g = Mth.clamp(g, 0, 1);
		this.b = Mth.clamp(b, 0, 1);
		return this;
	}

	public ParticleBuilder clr(int r, int g, int b) {
		return this.clr(r / 255f, g / 255f, b / 255f);
	}

	public ParticleBuilder clr(int hex) {
		return this.clr((hex & 0xFF0000) >> 16, (hex & 0xFF00) >> 8, hex & 0xFF);
	}

	public ParticleBuilder fade(float r, float g, float b) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.fr = Mth.clamp(r, 0, 1);
		this.fg = Mth.clamp(g, 0, 1);
		this.fb = Mth.clamp(b, 0, 1);
		return this;
	}

	public ParticleBuilder fade(int r, int g, int b) {
		return this.fade(r / 255f, g / 255f, b / 255f);
	}

	public ParticleBuilder fade(int hex) {
		return this.fade((hex & 0xFF0000) >> 16, (hex & 0xFF00) >> 8, hex & 0xFF);
	}

	public ParticleBuilder scale(float scale) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.scale = scale;
		return this;
	}

	public ParticleBuilder time(int lifetime) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.lifetime = lifetime;
		return this;
	}

	public ParticleBuilder seed(long seed) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.seed = seed;
		return this;
	}

	public ParticleBuilder spin(double radius, double speed) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.radius = radius;
		this.rpt = speed;
		return this;
	}

	public ParticleBuilder gravity(boolean gravity) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.gravity = gravity;
		return this;
	}

	public ParticleBuilder shaded(boolean shaded) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.shaded = shaded;
		return this;
	}

	public ParticleBuilder collide(boolean collide) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.collide = collide;
		return this;
	}

	public ParticleBuilder entity(Entity entity) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.entity = entity;
		return this;
	}

	public ParticleBuilder face(float yaw, float pitch) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.yaw = yaw;
		this.pitch = pitch;
		return this;
	}

	public ParticleBuilder face(Direction direction) {
		float hAngle = switch (direction) {
			case SOUTH -> 0f;
			case WEST -> 90f;
			case NORTH -> 180f;
			case EAST -> 270f;
			default -> 0f;
		};
		float vAngle = direction.getAxis().isVertical() ? direction.getAxisDirection().getStep() * -90f : 0f;
		return face(hAngle, vAngle);
	}

	public ParticleBuilder target(double x, double y, double z) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.tx = x;
		this.ty = y;
		this.tz = z;
		return this;
	}

	public ParticleBuilder target(Vec3 pos) {
		return target(pos.x, pos.y, pos.z);
	}

	public ParticleBuilder tvel(double vx, double vy, double vz) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.tvx = vx;
		this.tvy = vy;
		this.tvz = vz;
		return this;
	}

	public ParticleBuilder tvel(Vec3 vel) {
		return tvel(vel.x, vel.y, vel.z);
	}

	public ParticleBuilder length(double length) {
		this.length = length;
		return this;
	}

	public ParticleBuilder target(Entity target) {
		if (!building) throw new IllegalStateException("Not building yet!");
		this.target = target;
		return this;
	}

	/**
	 * Spawns the built particle. Client-side only.
	 * TODO: reimplement when CustomParticle is ported to the NeoForge particle engine.
	 */
	public void spawn(Level level) {
		if (!building) throw new IllegalStateException("Not building yet!");
		if (!level.isClientSide()) {
			MineFantasyReforged.LOG.warn("ParticleBuilder.spawn() called server-side — ignoring.");
		}
		reset();
	}

	private void reset() {
		building = false;
		type = null;
		x = 0; y = 0; z = 0;
		vx = Double.NaN; vy = Double.NaN; vz = Double.NaN;
		r = -1; g = -1; b = -1;
		fr = -1; fg = -1; fb = -1;
		radius = 0; rpt = 0;
		lifetime = -1;
		gravity = false; shaded = false; collide = false;
		scale = 1;
		entity = null;
		yaw = Float.NaN; pitch = Float.NaN;
		tx = Double.NaN; ty = Double.NaN; tz = Double.NaN;
		tvx = Double.NaN; tvy = Double.NaN; tvz = Double.NaN;
		target = null;
		seed = 0;
		length = -1;
	}

	public static ParticleBuilder create(Identifier type, Entity entity) {
		double px = entity.getX() + (entity.level().getRandom().nextDouble() - 0.5) * entity.getBbWidth();
		double py = entity.getY() + entity.level().getRandom().nextDouble() * entity.getBbHeight();
		double pz = entity.getZ() + (entity.level().getRandom().nextDouble() - 0.5) * entity.getBbWidth();
		return ParticleBuilder.instance.particle(type).pos(px, py, pz);
	}

	public static ParticleBuilder create(Identifier type, Random random, double x, double y, double z, double radius, boolean move) {
		double px = x + (random.nextDouble() * 2 - 1) * radius;
		double py = y + (random.nextDouble() * 2 - 1) * radius;
		double pz = z + (random.nextDouble() * 2 - 1) * radius;
		if (move) return ParticleBuilder.instance.particle(type).pos(px, py, pz).vel(px - x, py - y, pz - z);
		return ParticleBuilder.instance.particle(type).pos(px, py, pz);
	}
}
