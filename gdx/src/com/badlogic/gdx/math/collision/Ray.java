/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.math.collision;

import java.io.Serializable;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/** Encapsulates a ray having a starting position and a unit length direction
 * The direction vector must be a normalized vector for the proper functioning of this object.
 * 
 * @author badlogicgames@gmail.com */
public class Ray implements Serializable {
	private static final long serialVersionUID = -620692054835390878L;
	public final Vector3 origin = new Vector3();
	public final Vector3 direction = new Vector3();

	public Ray () { }
	
	/** Constructor, sets the starting position of the ray and the direction.
	 * 
	 * @param origin The starting position
	 * @param direction The direction(must be normalized before) */
	public Ray (Vector3 origin, Vector3 direction) {
		this.origin.set(origin);
		this.direction.set(direction);
	}

	/** Constructor, sets the starting position of the ray and the direction. */
	public Ray (Vector3 origin, Vector3 direction, boolean normalizeDirection) {
		this(origin, direction);
		if (normalizeDirection) this.direction.nor();
	}

	/** @return a copy of this ray. */
	public Ray cpy () {
		return new Ray(this.origin, this.direction);
	}

	/** Returns the endpoint given the distance. This is calculated as startpoint + distance * direction.
	 * @param out The vector to set to the result
	 * @param distance The distance from the end point to the start point.
	 * @return The out param */
	public Vector3 getEndPoint (final Vector3 out, final float distance) {
		return out.set(direction).scl(distance).add(origin);
	}

	static Vector3 tmp = new Vector3();

	/** Multiplies the ray by the given matrix. Use this to transform a ray into another coordinate system.
	 * 
	 * @param matrix The matrix
	 * @return This ray for chaining. */
	public Ray mul (Matrix4 matrix) {
		tmp.set(origin).add(direction);
		tmp.mul(matrix);
		origin.mul(matrix);
		direction.set(tmp.sub(origin));
		return this;
	}

	/** {@inheritDoc} */
	public String toString () {
		return "ray [" + origin + ":" + direction + "]";
	}

	/** Sets the starting position and the direction of this ray.
	 * 
	 * @param origin The starting position
	 * @param direction The direction (Vector must be normalized before, use vector.nor() or
	 *           {@link Ray#setNormalize(Vector3, Vector3)})
	 * @return this ray for chaining */
	public Ray set (Vector3 origin, Vector3 direction) {
		this.origin.set(origin);
		this.direction.set(direction);
		return this;
	}

	/** Sets the starting position and the direction of this ray.
	 * The direction vector will be normalized
	 * @param origin The starting position
	 * @param direction The direction
	 * @return this ray for chaining */
	public Ray setNormalize (Vector3 origin, Vector3 direction) {
		this.set(origin, direction);
		this.direction.nor();
		return this;
	}

	/** Sets this ray from the given starting position and direction.
	 * dx, dy, dz must give a Vector3 normalized (instead use
	 * {@link Ray#setNormalize(float, float, float, float, float, float)})
	 * @param x The x-component of the starting position
	 * @param y The y-component of the starting position
	 * @param z The z-component of the starting position
	 * @param dx The x-component of the direction
	 * @param dy The y-component of the direction
	 * @param dz The z-component of the direction
	 * @return this ray for chaining */
	public Ray set (float x, float y, float z, float dx, float dy, float dz) {
		this.origin.set(x, y, z);
		this.direction.set(dx, dy, dz);
		return this;
	}

	/** Sets this ray from the given starting position and direction.
	 * the direction vector will be normalized
	 * @param x The x-component of the starting position
	 * @param y The y-component of the starting position
	 * @param z The z-component of the starting position
	 * @param dx The x-component of the direction
	 * @param dy The y-component of the direction
	 * @param dz The z-component of the direction
	 * @return this ray for chaining */
	public Ray setNormalize (float x, float y, float z, float dx, float dy, float dz) {
		this.origin.set(x, y, z);
		this.direction.set(dx, dy, dz).nor();
		return this;
	}

	/** Sets the starting position and direction from the given ray
	 * 
	 * @param ray The ray
	 * @return This ray for chaining */
	public Ray set (Ray ray) {
		this.origin.set(ray.origin);
		this.direction.set(ray.direction);
		return this;
	}

	@Override
	public boolean equals (Object o) {
		if (o == this) return true;
		if (o == null || o.getClass() != this.getClass()) return false;
		Ray r = (Ray)o;
		return this.direction.equals(r.direction) && this.origin.equals(r.origin);
	}

	@Override
	public int hashCode () {
		final int prime = 73;
		int result = 1;
		result = prime * result + this.direction.hashCode();
		result = prime * result + this.origin.hashCode();
		return result;
	}
}
